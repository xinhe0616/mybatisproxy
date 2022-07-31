package com.ym.mybatisproxy.proxy;

import com.ym.mybatisproxy.domain.Buser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.type.IntegerTypeHandler;
import org.apache.ibatis.type.StringTypeHandler;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperProxyFactory {
    //处理类型
    private static Map<Class, TypeHandler> typeHandlerMap = new HashMap<>();

    //加载驱动
    static {
        typeHandlerMap.put(String.class,new StringTypeHandler());
        typeHandlerMap.put(Integer.class,new IntegerTypeHandler());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //代理对象
    public static <T> T getMapper(Class<T> mapper){
        //jdk的动态代理
        Object proxyInstance = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{mapper}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /***
                 * jdbc实现
                 */
                //创建连接
                Connection connection = getConnection();

                Select annotation = method.getAnnotation(Select.class);
                String[] sql = annotation.value();
//                System.out.println(sql);

                //参数映射
                HashMap<String, Object> map = new HashMap<>();
                Parameter[] parameters = method.getParameters();
                for(int i=0;i<parameters.length;i++){
                    Parameter parameter = parameters[i];
                    String param = parameter.getAnnotation(Param.class).value();
                    map.put(parameter.getName(),args[i]);
                    map.put(param,args[i]);
                }
//                ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
//                GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
//                String parse = parser.parse(sql);
//                List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();

                //预编译，提高性能，防止sql注入
//                PreparedStatement statement = connection.prepareStatement(parse);
//                for (int i = 0; i < parameterMappings.size(); i++) {
//                    String property = parameterMappings.get(i).getProperty();
//                    Object value = map.get(property);
//                    Class<?> type = value.getClass();
//                    typeHandlerMap.get(type).setParameter(statement,i+1,value);
//                }
//                statement.execute();

                //获取结果
                ArrayList<Buser> list = new ArrayList<>();
//                ResultSet resultSet = statement.getResultSet();
//                while (resultSet.next()){
//                    Buser buser = new Buser();
//                    buser.setId(resultSet.getInt("id"));
//                    buser.setUser(resultSet.getString("user"));
//                    buser.setPass(resultSet.getString("pass"));
//                    list.add(buser);
//                }
//                connection.close();
                return list;
            }
        });
        return (T) proxyInstance;
    }

    private static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.2.134:3306/base?useUnicode=true&serverTimezone=Asia/Shanghai&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowMultiQueries=true",
                "root", "123456");
        return connection;
    }
}

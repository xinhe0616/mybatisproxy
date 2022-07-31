package com.ym.mybatisproxy.proxy;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class,
                Object.class,
                RowBounds.class,
                ResultHandler.class})})
public class MySqlInterceptor implements Interceptor {



    /**
     * 日志记录器
     */
    private static final Logger logger= LoggerFactory.getLogger(MySqlInterceptor.class);
    private String db;

    public MySqlInterceptor(String db) {
        this.db = db;
    }

    /**
     * intercept 方法用来对拦截的 sql 进行具体的操作
     * 本拦截方法只对 mapper 接口中的 searchByQuery 方法进行拦截，实际每个方法都拦截了，
     * 只是只有 searchByQuery 方法时，才真正执行 拦截的相关操作
     * @param invocation 拦截器执行器
     * @return
     * @throws Throwable 异常信息
     */


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MapperProxyFactory proxyFactory = new MapperProxyFactory();
        logger.info("拦截了 " + "123" + " 相关执行。");
        Executor executor = (Executor) invocation.getTarget();
        List<Object> resList = new ArrayList<>();
        if (db.equals("cloudDb")){

            return resList;
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}

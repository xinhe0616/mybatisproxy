package com.ym.mybatisproxy.proxy;


import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
//@AutoConfigureAfter(PageHelperAutoConfiguration.class)
public class MyBatisConfig {
    @Value("${db.select}")
    String db="base";

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addMySqlInterceptor() {
        MySqlInterceptor interceptor = new MySqlInterceptor(db);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);

        }
    }

}

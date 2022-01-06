package com.ln.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import javax.sql.DataSource;

//springboot就是通过运行这个类的main来启动springboot应用
@SpringBootApplication()
@EnableCaching //开启缓存注解
//springboot项目的主配置类
public class SpringdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringdemoApplication.class, args);
    }

}

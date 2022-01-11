package com.ln.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.sql.DataSource;

//springboot就是通过运行这个类的main来启动springboot应用
@SpringBootApplication()
//项目中我们对于不易改动的信息没必要每次都去数据库查询，可以将查询结果放入缓存中，第二次调用时，直接在缓存中获取，不再经过数据库
@EnableCaching //开启缓存注解
//springboot项目的主配置类
public class SpringdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringdemoApplication.class, args);
    }

}

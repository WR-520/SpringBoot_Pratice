package com.ln.springdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig{
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                // 用来创建API页面的基本信息，
                .apiInfo(apiInfo())
                // 是否开启Swagger UI页面，默认是开启
                .enable(true)
                // 创建构造器，用于定义Swagger生成文档中包含哪些接口和方法
                .select()
                // 扫描包路径
                //.apis(RequestHandlerSelectors.basePackage("com.ln.springdemo.service"))
                // 扫描方法
                //.apis(RequestHandlerSelectors.withMethodAnnotation("com.ln.springdemo.controller.userController.showUser"))
                // 扫描类
                //.apis(RequestHandlerSelectors.withMethodAnnotation("com.ln.springdemo.controller.userController"))
                // 扫描任何接口
                //.apis(RequestHandlerSelectors.any())
                // 开发过程中一般扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.ln.springdemo.controller"))
                // 根据请求路径定义当前Docker容器需要包含控制器中的哪些方法
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }


    //构造接口文件基础化信息
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("项目开发-SpringBoot项目集成Swagger2进行接口管理 DEMO")
                .description("SpringBoot项目前后端分离项目实训")
                .contact(new Contact("岭南学院","http://localhost:9101/",""))
                .version("2021.12.30")
                .build();
    }

}

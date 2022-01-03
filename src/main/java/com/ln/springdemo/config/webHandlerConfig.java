package com.ln.springdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class webHandlerConfig implements WebMvcConfigurer {

    //用这个方法来注册拦截器，我们自定义需要注册到这个方法中才能生效
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new HandleInterceptorConfig())
                .addPathPatterns("/**")//拦截所有请求
                .excludePathPatterns("/login")//放行对应请求
                .excludePathPatterns("/user/getNumCode")//放行验证码
                .excludePathPatterns("swagger-resources/**","/webjars/**","/swagger-ui.html/**")
                .excludePathPatterns("/user/login")//放行登录操作
                .excludePathPatterns("/static/**")//放行静态资源
                .excludePathPatterns("/swagger-ui.html");//
    }

    /**
     * 配置swagger2的静态资源路径
     * 如果继承了WebMvcConfigurationSupport，
     *          则在配置文件在中配置的相关内容会失效，需要重新指定静态资源
     *
     * 需要重新指定swagger静态资源
     */
    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问 【静态资源路径】
        registry.addResourceHandler("/resources/**") //表示文件路径
                .addResourceLocations("classpath:/resources/static/"); //表示要放开的资源
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
}

    public void addViewControllers(ViewControllerRegistry registry){
        //映射主页面
        registry.addViewController("/index").setViewName("/home/index");
        //初始化主页面
        registry.addViewController("/adminWelcome").setViewName("/page/welcome-1");
        //映射登陆页面
        registry.addViewController("/login").setViewName("login/login");
        //映射图片添加页面
        registry.addViewController("imgs/addImage").setViewName("/imgs/addImage");
        registry.addViewController("imgs/imgHtml").setViewName("/imgs/images");
    }


}

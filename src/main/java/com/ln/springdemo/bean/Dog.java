package com.ln.springdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data // 封装get/set toString
@AllArgsConstructor //全参构造函数
@NoArgsConstructor //无参构造函数
@Component //当组件不好归类时 可以使用跟着注解进行标注， 标识一个Bean
/**
 @Controller: controller控制层
 @Service: service层，业务层组件
 @Repository: dao层，数据访问组件，Dao组件
 */
public class Dog {
    @Value("啊邓")
    public String name;
    @Value("3")
    private Integer age;

}

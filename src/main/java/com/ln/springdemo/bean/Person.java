package com.ln.springdemo.bean;

import com.ln.springdemo.bean.Dog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
//@Data 注解的主要作用是提高代码的简洁，
// 使用这个注解可以省去代码中大量的
// get()、 set()、 toString()等方法；
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component //注入bean
/**
 * @ConfigurationProperties
 *  将配置文件中的每一个属性的值，映射到这个组件中
 *  进行属性的绑定
 *  参数prefix = "person" 将配置文件中的person下面的属性一一对应
 */
@ConfigurationProperties(prefix = "person")

public class Person {
    private String name;
    private Integer age;
    private Date birthday;
    private Map<String, Object> maps;
    private List<Object> lists;
    private Dog dog;
    private Boolean happy;
}

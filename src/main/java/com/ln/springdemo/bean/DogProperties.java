package com.ln.springdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
//@PropertySource是加载指定的配置文件。
@PropertySource(value = "classpath:dog.properties")
public class DogProperties {
    @Value("${name}")
    private String name;
    @Value("${age}")
    private Integer age;
}

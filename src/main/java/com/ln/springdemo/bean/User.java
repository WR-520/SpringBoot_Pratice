package com.ln.springdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 实现：
 *  1.实体类映射
 *  2.UserRepository
 *  3.UserService
 *  4.UserController
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
/**
 * @ConfigurationProperties
 *  将配置文件中的每一个属性的值，映射到
 */

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userName;

    private String password;
    private String iphone;
    private String createTime;
}

package com.ln.springdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 实现：
 *  1.实体类映射
 *  2.UserRepository
 *  3.UserService
 *  4.UserController
 */

/**
 * @ConfigurationProperties
 *  将配置文件中的每一个属性的值，映射到
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userName;

    private String password;
    private String iphone;
    private String createTime;

}

package com.ln.springdemo.bean;

import lombok.*;

import javax.persistence.*;

/**
 * 映射操作 class映射成数据库表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity //指定当前类是实体类
@Table(name = "book") //指定实体类和表之间的对应关系

public class Book {
    @Id //指定当前字段是主键
    @GeneratedValue(strategy = GenerationType.TABLE)//指定主键的生成方式
    private int id;
    /**
     * @GenericGenerator(name="idGenerator",strategy="uuid")
     * @GeneratedValue(generator = 'idGenerator')
     * private String strId; //主键 ===>mysql 字符串类型的主键生成策略
     */
    /**
     * Column注解属性：
     * name: 指定数据库表字段名称
     * unique: 是否唯一
     */

    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;
    @Column(name = "author", nullable = false, length = 20)
    private String author;
    @Column(name = "price", nullable = false, length = 10)
    private double price;

    private Integer sales;

    private Integer stock;
    @Column(name = "imgPath", unique = true, nullable = false, length = 30)
    private String imgPath;
    @Column(name = "createTime", nullable = true, length = 100)
    private String createTime;

}

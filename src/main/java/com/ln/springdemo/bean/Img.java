package com.ln.springdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    @Column(nullable = false)
//    图片等级

    private float plevelNum;
//    @Column(name = "imgT")
//    图片类型
    @Column(nullable = false)
    private String imgType;
//    图片是否显示
    @Column(nullable = false)
    private String imgOpen;

    @Column(nullable = false)
    private String imgPath;
    @Column(nullable = false)
    private String imgdesc;

    @Column(nullable = false)
    private String createTime;
    @Column(nullable = false)
    private String imgName;

}

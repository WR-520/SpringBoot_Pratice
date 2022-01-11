package com.ln.springdemo.Repository;

import com.ln.springdemo.bean.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
//@Repository持久层组件 用于标注数据访问组件
@Repository
public interface ImgRepository extends JpaRepository<Img,Integer>, JpaSpecificationExecutor<Img> {

}

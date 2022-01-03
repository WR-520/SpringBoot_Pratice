package com.ln.springdemo.Repository;

import com.ln.springdemo.bean.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository extends JpaRepository<Img,Integer>, JpaSpecificationExecutor<Img> {

}

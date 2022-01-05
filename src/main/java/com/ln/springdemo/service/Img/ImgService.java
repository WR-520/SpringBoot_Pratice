package com.ln.springdemo.service.Img;

import com.ln.springdemo.bean.Img;

import java.util.Map;

public interface ImgService {
    public boolean save(Img img);
    //分页条件查询
    Map<String, Object> page(int pageNum, int pageSize);
    void deleById(String id);
    Img findById(Integer id);
}

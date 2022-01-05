package com.ln.springdemo.service.Img.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.ln.springdemo.Repository.ImgRepository;
import com.ln.springdemo.bean.Book;
import com.ln.springdemo.bean.Img;
import com.ln.springdemo.service.Img.ImgService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ImgServiceImpl implements ImgService {
   @Resource
    private ImgRepository imgRepository;
   @Override
    public boolean save(Img img){
       Img imgBean = imgRepository.save(img);
       if(ObjectUtil.isNotNull(imgBean)){
           return true;
       }else{
           return false;
       }
   }

    @Override
    public Map<String, Object> page(int pageNum, int pageSize) {
        //        分页，排序，排列规则:按id降序
        Map<String, Object> map = new HashMap<>();
//     JPA分页之PageRequest.of代替过时的PageRequest方法
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
//      Example<实体> example = Example.of(实体)
        Page<Img> page = imgRepository.findAll(pageRequest);
        map.put("TotalElements", page.getTotalElements());
        map.put("pageContent", page.getContent());
        return map;
    }

    @Override
    public void deleById(String id) {
        imgRepository.deleteById(Integer.parseInt(id) );
    }

    @Override
    public Img findById(Integer id) {
        return  imgRepository.findById(id).get();

    }

}

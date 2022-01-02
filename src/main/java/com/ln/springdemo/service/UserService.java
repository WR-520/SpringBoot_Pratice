package com.ln.springdemo.service;

import com.ln.springdemo.Repository.UserRepository;
import com.ln.springdemo.bean.User;

public interface UserService  {
    /**
     *  1、方法命名方式进行绑定查询
     *  2、基于@query注解的方式进行查询
     */
   public User login(String userName, String password);
   public boolean save(User user);
   public User findById(Integer id);
   public int updatePasswordById(int id,String password);
}


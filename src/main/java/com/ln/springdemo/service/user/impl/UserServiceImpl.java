package com.ln.springdemo.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ln.springdemo.Repository.UserRepository;
import com.ln.springdemo.bean.User;
import com.ln.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
 @Autowired
    UserRepository userRepository;
 @Override
    public User login(String userName,String password){
     return userRepository.login(userName,password);
 }

    @Override
    public boolean save(User user) {
        User userBean = userRepository.save(user);
        return ObjectUtil.isNotNull(userBean);
    }
    @Override
    public User findById(Integer id){
       return userRepository.findById(id).get();
    }

    @Override
    public int updatePasswordById(int id,String password) {

        return userRepository.updatePassword(id,password);
    }
}

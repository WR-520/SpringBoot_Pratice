package com.ln.springdemo;

import cn.hutool.core.date.DateUtil;
import com.ln.springdemo.bean.User;
import com.ln.springdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
 @Autowired
    UserService userService;
 @Test
    public void saveTest(){
     User user = new User();
     user.setUserName("admin");
     user.setPassword("admin");
     user.setIphone("14778799888");
     user.setCreateTime(DateUtil.now());
     boolean bool = userService.save(user);
     System.out.println("======="+bool);
 }
  @Test
 public void longinTest(){
  System.out.println( userService.login("admin","admin"));
 }
}

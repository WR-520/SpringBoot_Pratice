package com.ln.springdemo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.ln.springdemo.bean.User;
import com.ln.springdemo.service.UserService;
import com.ln.springdemo.tools.ResultVo;
import com.ln.springdemo.tools.ResultVoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
//  登录
    @PostMapping("/login")
    @ResponseBody
    public ResultVo login(@RequestBody JSONObject jsonObject, Model model, HttpServletRequest request){
        String username = jsonObject.getStr("username");
        String password = jsonObject.getStr("password");
        String code = jsonObject.getStr("captcha");
        if( StrUtil.hasBlank(username) && StrUtil.hasBlank(password) ) {
            return ResultVoUtils.error("用户名或密码不能为空", "登录操作");
        }else{
        if(StrUtil.hasBlank(code)){
                return ResultVoUtils.error("验证码不能为空","登录操作");
            }
            else{
                String strCode =request.getSession().getAttribute("code").toString();
                if( strCode.equals(code)){
//                 数据库查询操作
                    User user=  userService.login(username,password);
//                 存储登录的用户信息
                 request.getSession().setAttribute("user",user);
                    if(ObjectUtil.isNotNull(user)){
                        return ResultVoUtils.success("登录成功","用户登录操作",user);
                    }else{
                        return ResultVoUtils.error("用户名或密码不正确","登录操作");
                    }
                }else{
//                    给出提示信息，验证码不正确
                     return ResultVoUtils.error("验证码不能正确","登录操作");
                }
            }
        }
    }

//    构造验证码
    @GetMapping("/getNumCode")
    public void getNumCode(HttpServletResponse response, HttpSession session) throws IOException {
//        定义图形验证码的长和宽、验证码字符数、干扰元素个数
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(120,40,4,20);
//        获取验证码
        captcha.getCode();
        session.setAttribute("code",captcha.getCode());
        System.out.println("验证码====="+captcha.getCode());
//        ServletOutputStream
        ServletOutputStream outputStream = response.getOutputStream();
        captcha.write(outputStream);
        outputStream.close();
    }

    @GetMapping("/showUser")
    public String showUser(int id,Model model){
      User user =  userService.findById(id);
      model.addAttribute("user",user);
      return "/user/user-setting";
    }
   @GetMapping("/toChangePassword")
    public String changePassword(int id,Model model){
        return "/user/user-password";
   }
   @PostMapping("/changePassword")
   @ResponseBody
    public ResultVo changePassword(@RequestBody JSONObject jsonObject, Model model , HttpServletRequest request) {

        //       旧密码
       String old_password = jsonObject.getStr("old_password");
//       新密码
       String new_password = jsonObject.getStr("new_password");
//       再次确认密码
       String again_password = jsonObject.getStr("again_password");
       System.out.println(old_password+"=========");
       System.out.println(new_password+"=========");
       System.out.println(again_password + "=======");
       if (StrUtil.hasBlank(old_password) && StrUtil.hasBlank(new_password) && StrUtil.hasBlank(again_password)) {
           return ResultVoUtils.error("密码输入不能为空", "修改密码操作");
       } else if (old_password.equals(new_password)) {
           return ResultVoUtils.error("旧密码不能和新密码相同", "修改密码操作");
       } else if (!new_password.equals(again_password)) {
           return ResultVoUtils.error("再次输入新密码错误", "修改密码操作");
       } else {
           User user = (User) request.getSession().getAttribute("user");
           int userid = user.getId();
           int flag = userService.updatePasswordById(userid, again_password);
           return ResultVoUtils.success("修改密码成功","修改密码操作");
       }
   }

   @GetMapping("/loginOut")
   public String loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "/login";
   }
}


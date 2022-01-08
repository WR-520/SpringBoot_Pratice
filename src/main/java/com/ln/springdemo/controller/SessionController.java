package com.ln.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class SessionController {
    @GetMapping("/getSession")
    public String getSession(HttpSession session){
       UUID uid = (UUID) session.getAttribute("uid");
       if(uid == null){
           uid = UUID.randomUUID();
       }
       session.setAttribute("uid",uid);
       return session.getId();
    }
}

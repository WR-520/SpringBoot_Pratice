package com.ln.springdemo.config;

import com.ln.springdemo.bean.User;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandleInterceptorConfig implements HandlerInterceptor {
    /**
     *
     * 拦截器实现思路
     * 判断session是否存在登录的对象
     *   1.如果存在返回true 放行
     *   2.如果不存在返回false 拦截
     *
     */
    //     在请求处理方法之前进行调用（Controller方法调用之前调用）
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("======>之前调用");
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            request.setAttribute("msg","无权限请先登录");
            response.sendRedirect(request.getContextPath() + "/login");
//            返回值设置为false 被请求时 进行拦截 拦截器执行到此处将不会再继续往下执行

            return false;
        }

        return true;
    }

//    请求处理之后进行调用，在视图被渲染之前（调用）
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("========>之中调用");
    }
//    在整个请求结束之后被调用，也就是在视图渲染完成之后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("======>之后调用");
    }
}

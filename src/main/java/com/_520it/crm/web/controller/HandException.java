package com._520it.crm.web.controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class HandException {

    @ExceptionHandler(UnauthorizedException.class)
    public void handException(HandlerMethod handlerMethod, HttpServletResponse response)  {
        try {
        //根据方法上是否贴有responseBody注解来判断是页面请求还是ajax请求
        if (handlerMethod.getMethodAnnotation(ResponseBody.class)!=null){
            //ajax请求
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("{\"success\":false,\"msg\":\"您没有权限\",\"total\":0,\"rows\":[]}");
        }else{
            //页面请求
            response.sendRedirect("/nopermission.jsp");
        }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

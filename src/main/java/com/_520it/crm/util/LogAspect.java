package com._520it.crm.util;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.SystemLog;
import com._520it.crm.service.ISystemLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class LogAspect {

    @Setter
    private ISystemLogService systemLogService;

    public void write(JoinPoint jp) throws JsonProcessingException {
        System.out.println("写日志---------------------");
        SystemLog log = new SystemLog();
        log.setOptime(new Date());
        Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
        log.setOpuser(current);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        log.setOplp(ip);
        String fuction = jp.getTarget()+":"+jp.getSignature().getName();
        log.setFunction(fuction);
        ObjectMapper objectMapper = new ObjectMapper();
        log.setParams(objectMapper.writeValueAsString(jp.getArgs()));
        systemLogService.insert(log);
    }
}





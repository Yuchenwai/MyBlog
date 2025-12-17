package com.zhoufeng.myblog.aspect;

import com.zhoufeng.myblog.entity.Log;
import com.zhoufeng.myblog.mapper.LogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private LogMapper logMapper;
    @Resource
    HttpServletRequest request;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.zhoufeng.myblog.controller..*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        String ip = request.getRemoteAddr();
        String url = String.valueOf(request.getRequestURL());
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] objects = joinPoint.getArgs();
        // 兼容参数中包含 null 的情况，避免 NPE，同时限制长度防止写入数据库时超长
        String args = Arrays.stream(objects)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        // 假设 log.args 列为 VARCHAR(255)，超出部分截断
        if (args.length() > 255) {
            args = args.substring(0, 255);
        }
        Log log = new Log();
        log.setIp(ip);
        log.setUrl(url);
        log.setMethod(method);
        log.setArgs(args);
        log.setOperateTime(LocalDateTime.now());
        logMapper.insert(log);
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void afterReturn(Object result) {
        // 兼容返回值为 null 的情况
        logger.info(String.valueOf(result));
    }
}

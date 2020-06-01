package com.jz.aspect;

import com.jz.common.SSLUtils;
import com.jz.domain.VisitorInfo;
import com.jz.mapper.VisitorInfoMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/6
 */
@Aspect
@Component
public class MovieAspect {

    private static final Logger logger = LoggerFactory.getLogger(MovieAspect.class);

    @Resource
    VisitorInfoMapper visitorInfoMapper;

    //配置切点
    @Pointcut("execution(public  * com.jz.service.impl..*(..))")
    public void log(){
    }

    //前置通知
    @Before("log()")
    public void  before(JoinPoint joinPoint) throws Exception {
    //获取SSL证书
    SSLUtils.trustAllHttpsCertificates();
    //纪录访问ip的相关信息
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    HttpServletRequest request = requestAttributes.getRequest();

        VisitorInfo visitorInfo = new VisitorInfo
                (null,request.getRemoteAddr(),
                        request.getRequestURL().toString(),
                        new Date(),"",
                        joinPoint.getSignature().getDeclaringType()+"."+joinPoint.getSignature().getName(),
                        Arrays.toString(joinPoint.getArgs())
                        );

        //url
        logger.info("url={}",request.getRequestURL());
        //method
        logger.info("method={}",request.getMethod());
        //ip
        logger.info("ip={}",request.getLocalAddr());
        //类方法
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringType()+"."+joinPoint.getSignature().getName());
        //方法参数
        logger.info("args={}",joinPoint.getArgs());

        this.insertVisitor(visitorInfo);

    }

    private String getAddress(String ip) throws IOException {

        if ("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }

        Document document = Jsoup.connect("http://ip.tool.chinaz.com/" + ip).get();

        return document.select(".WhoIpWrap .bor-b1s .Whwtdhalf").last().text();
    }


    @Async
    public void insertVisitor(VisitorInfo visitorInfo) throws IOException {

        String address = getAddress(visitorInfo.getIp());

        visitorInfo.setAddress(address);

        visitorInfoMapper.insert(visitorInfo);

        logger.info("visitor={}",visitorInfo);
    }


}

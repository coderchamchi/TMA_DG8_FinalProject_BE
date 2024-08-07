//package com.bezkoder.springjwt.config;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class TestServiceAspect {
//    private final Logger logger = LoggerFactory.getLogger(TestServiceAspect.class);
//
//    @Before("execution(* com.bezkoder.springjwt.controllers..*(..))")
//    public void before(JoinPoint joinPoint){
//        logger.info("Before called " + joinPoint.toString());
//    }
//    @AfterReturning("execution(* com.bezkoder.springjwt.controllers..*(..))")
//    public void afterReturn(JoinPoint joinPoint){
//        logger.info("Response: " + joinPoint.toString());
//    }
//    @After("execution(* com.bezkoder.springjwt.controllers..*(..))")
//    public void after(JoinPoint joinPoint){
//        logger.info("After call" + joinPoint.toString());
//    }
//}

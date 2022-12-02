package com.basic.myspringboot.runner;

import com.basic.myspringboot.property.MyBootProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(MyRunner.class);
    @Value("${myboot.name}")
    private String name;
    @Value("${myboot.age}")
    private int age;

    @Autowired
    Environment environment;

    @Resource(name= "myBootProperty")
    private MyBootProperties myBootProperties;

    @Autowired
    private String hello;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("Logger 구현 클래스 이름 {}", logger.getClass().getName());

        logger.debug("현재 활성화된 hello Bean = " + hello);
        logger.debug("MyBootProperties Name = " + myBootProperties.getName());
        logger.debug("MyBootProperties Age = " + myBootProperties.getAge());
        logger.debug("MyBootProperties fullName = " + myBootProperties.getFullName());
        logger.debug("${myboot.name} = " + name);
        logger.debug("${myboot.age} = " + age);
        String fullName = environment.getProperty("myboot.fullName");
        logger.debug("${myboot.fullName} = " + fullName);

        // java -jar -Dbar .\target\myspringbootApp-0.0.1-SNAPSHOT.jar --foo
        logger.info("현재 활성화 된 Profile = {}", environment.getProperty("spring.profiles.active"));
        logger.info("Program Argument : " + args.containsOption("foo"));
        logger.info("VM Argument : " + args.containsOption("bar"));
    }
}
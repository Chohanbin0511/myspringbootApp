package com.basic.myspringboot.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Component
@Order(1)
@Slf4j
public class DBRunner implements ApplicationRunner {
    @Autowired
    private DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("DataSource 구현 객체 클래스명 =  {}",dataSource.getClass().getName());
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        log.info(">> DB URL = {}", metaData.getURL());
        log.info(">> DB Vendor Name = {}", metaData.getDatabaseProductName());
        log.info(">> DB Username = {}", metaData.getUserName());
        log.info(">> DB ProductName = {}", metaData.getDatabaseProductName());
        log.info(">> DB ProductVersion = {}", metaData.getDatabaseProductVersion());

    }
}

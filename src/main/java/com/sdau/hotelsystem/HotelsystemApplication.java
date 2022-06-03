package com.sdau.hotelsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.sdau.hotelsystem.mapper")
public class HotelsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelsystemApplication.class, args);
    }

}

package com.example.ystand;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.ystand.Dao.mapper")
public class YStandApplication {

    public static void main(String[] args) {
        SpringApplication.run(YStandApplication.class, args);
    }

}

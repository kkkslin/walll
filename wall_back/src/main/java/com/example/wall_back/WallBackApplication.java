package com.example.wall_back;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
//@ComponentScan("com.example.wall_back.mapper")
@MapperScan("com.example.wall_back.mapper")
public class WallBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(WallBackApplication.class, args);
    }

}

package com.shb.springsecurityjsonlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shb.springsecurityjsonlogin.mapper")
public class SpringSecurityJsonLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJsonLoginApplication.class, args);
    }

}

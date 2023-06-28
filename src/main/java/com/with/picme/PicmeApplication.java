package com.with.picme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PicmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PicmeApplication.class, args);
    }

}

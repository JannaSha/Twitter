package com.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.comment")
//@PropertySource("classpath:db-config-comment.properties")
public class CommentApplication {
    public static void main(String[] args) {
//        System.setProperty("spring.config.name", "application");
        SpringApplication.run(CommentApplication.class, args);
    }
}

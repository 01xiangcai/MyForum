package com.yao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @className: MyForumApplication
 * @Description: TODO
 * @author: long
 * @date: 2023/3/6 17:06
 */
@SpringBootApplication
@EnableWebMvc
public class MyForumApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyForumApplication.class,args);
    }
}

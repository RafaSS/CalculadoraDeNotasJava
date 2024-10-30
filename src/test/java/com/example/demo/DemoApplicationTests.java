package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.example.demo.controller", "com.example.demo.servico", "com.example.demo.repository", "com.example.demo.exception", "com.example.demo.modelo", "com.example.demo.exception"})
@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {

    }

}

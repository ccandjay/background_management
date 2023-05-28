package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@RestController
@MapperScan("com.marker.mapper")
@EnableWebMvc // 解决swagger3.0的兼容性问题
public class BootVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootVueApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //指定文件代销
        factory.setMaxFileSize(DataSize.ofBytes(10485760*1024));
        /// 设定上传文件大小
        factory.setMaxRequestSize(DataSize.ofBytes(10485760*1024));
        return factory.createMultipartConfig();
    }

}

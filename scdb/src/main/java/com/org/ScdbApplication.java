package com.org;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.org.controller","com.org.serviceImpl","com.org.config","com.org.Interceptor","com.org.utils"})
@MapperScan(basePackages = "com.org.mapper")
@SpringBootApplication
@EnableCaching
public class ScdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScdbApplication.class, args);
	}

}

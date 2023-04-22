package com.org.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 //开启swagger
public class SwaggerConfig {
    //配置swagger的docket的实例
    @Bean
    public Docket docket(Environment environment){
        //设置swagger在什么环境下显示
        Profiles profiles=Profiles.of("dev");

        //判断swagger所处环境
        boolean flag=environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.org.controller"))
                .build();
    }

    //配置swagger文档信息apiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact=new Contact("刘文鑫","http://localhost:8888","1041835408@qq.com");

        return new ApiInfo(
                "学生选课管理系统api文档",
                "第一次使用swagger自动生成文档",
                "1.0",
                "http://localhost:8888",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
}

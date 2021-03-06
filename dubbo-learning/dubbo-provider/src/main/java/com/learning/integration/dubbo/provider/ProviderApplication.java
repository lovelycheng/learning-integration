package com.learning.integration.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:spring/dubbo-demo-provider.xml", "classpath*:applications.properties"})
@ComponentScan(basePackages = {"com.learning.integration.dubbo.provider" +
        ".impl", "com.learning.integration.dubbo.provider.spitest"})
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

}

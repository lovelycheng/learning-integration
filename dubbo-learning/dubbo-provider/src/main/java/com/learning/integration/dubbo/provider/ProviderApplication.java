package com.learning.integration.dubbo.provider;

import com.learning.integration.dubbo.provider.impl.MaybeMainClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
@SpringBootApplication
@ImportResource("classpath*:spring/dubbo-demo-provider.xml")
@ComponentScan(basePackageClasses =  MaybeMainClass.class)
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }

}

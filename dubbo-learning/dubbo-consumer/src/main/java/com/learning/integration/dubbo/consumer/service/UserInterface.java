package com.learning.integration.dubbo.consumer.service;

import com.learning.integration.dubbo.provider.api.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author chengtong
 * @date 2019-10-03 20:09
 */
@Service
public class UserInterface {

    @Autowired
    DemoService demoService;

    @PostConstruct
    public void sss(){
        demoService.sayHello("dubbo ");
    }



}

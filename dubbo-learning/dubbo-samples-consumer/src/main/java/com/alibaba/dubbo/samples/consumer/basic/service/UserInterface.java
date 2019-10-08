package com.alibaba.dubbo.samples.consumer.basic.service;

import com.alibaba.dubbo.samples.provider.basic.api.DemoService;
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

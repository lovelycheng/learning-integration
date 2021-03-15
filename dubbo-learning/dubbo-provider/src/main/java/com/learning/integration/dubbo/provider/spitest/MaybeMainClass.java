package com.learning.integration.dubbo.provider.spitest;

import com.learning.integration.dubbo.provider.api.SpiService;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.ServiceLoader;

/**
 * @author chengtong
 * @date 2019-10-22 10:44
 */
@Component
public class MaybeMainClass implements SmartLifecycle {
    @Override
    public void start() {
        ServiceLoader<SpiService> spiServiceServiceLoader = ServiceLoader.load(SpiService.class);

        for (SpiService spiService : spiServiceServiceLoader) {
            spiService.saySpi();
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}

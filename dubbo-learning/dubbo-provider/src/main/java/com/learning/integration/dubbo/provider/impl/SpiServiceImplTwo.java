package com.learning.integration.dubbo.provider.impl;

import com.learning.integration.dubbo.provider.api.SpiService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chengtong
 * @date 2019-10-22 10:42
 */
@Slf4j
public class SpiServiceImplTwo implements SpiService {

    @Override
    public void saySpi() {
        log.error("====================  ppp ===================");
    }
}

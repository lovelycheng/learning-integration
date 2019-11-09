package com.learning.integration.dubbo.provider.impl;

import com.learning.integration.dubbo.provider.api.SpiService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chengtong
 * @date 2019-10-20 22:42
 */
@Slf4j
public class SpiServiceImpl implements SpiService {

    @Override
    public void saySpi() {
        log.info("+===================== service from spi =======================");
    }

}

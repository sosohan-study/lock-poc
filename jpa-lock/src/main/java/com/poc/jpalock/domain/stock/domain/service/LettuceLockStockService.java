package com.poc.jpalock.domain.stock.domain.service;

import com.poc.jpalock.domain.common.RedisLockRepository;
import com.poc.jpalock.domain.stock.domain.model.StockCommand;
import com.poc.jpalock.domain.stock.domain.model.StockInfo;
import com.poc.jpalock.global.annotation.FacadeService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@FacadeService
public class LettuceLockStockService {

    private final RedisLockRepository redisLockRepository;

    private final StockService stockService;

    public StockInfo decreaseWithSpinLock(final StockCommand stockCommand) throws InterruptedException {
        while (!redisLockRepository.lock(stockCommand.id())) {
            Thread.sleep(100L);
        }
        try {
            return stockService.decreaseWithSpinLock(stockCommand);
        } finally {
            redisLockRepository.unLock(stockCommand.id());
        }
    }
}

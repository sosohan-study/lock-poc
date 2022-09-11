package com.poc.jpalock.domain.stock.domain.service;

import com.poc.jpalock.domain.stock.domain.model.StockCommand;
import com.poc.jpalock.domain.stock.domain.model.StockInfo;
import com.poc.jpalock.global.annotation.FacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@FacadeService
public class RedissonLockStockService {

    private final RedissonClient redissonClient;
    private final StockService stockService;

    public StockInfo decreaseWithDistributedLock(final StockCommand stockCommand) {
        final RLock lock = redissonClient.getLock(stockCommand.id().toString());

        try {
            final boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);
            if (!available) {
                log.info("Lock 획득 실패");
                return stockService.findById(stockCommand.id());
            }
            return stockService.decreaseWithDistributedLock(stockCommand);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}

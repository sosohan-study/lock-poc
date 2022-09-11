package com.poc.jpalock.domain.stock.domain.service;

import com.poc.jpalock.domain.stock.domain.model.StockCommand;
import com.poc.jpalock.domain.stock.domain.model.StockInfo;
import com.poc.jpalock.global.annotation.FacadeService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@FacadeService
public class OptimisticLockStockService {

    private final StockService stockService;

    public StockInfo decreaseWithOptimisticLock(final StockCommand stockCommand) throws InterruptedException {
        StockInfo stockInfo = null;
        while (true) {
            try {
                stockInfo = stockService.decreaseWithOptimisticLock(stockCommand);
                break;
            } catch (Exception e) {
                Thread.sleep(500);
            }
        }
        return stockInfo;
    }
}

package com.poc.jooqlock.domain.stock.domain.service;

import com.poc.jooqlock.domain.stock.domain.model.StockCommand;
import com.poc.jooqlock.domain.stock.domain.model.StockInfo;
import com.poc.jooqlock.domain.stock.domain.reader.StockReader;
import com.poc.jooqlock.domain.stock.domain.store.StockStore;
import com.poc.jooqlock.global.config.annotation.FacadeService;
import org.springframework.transaction.annotation.Transactional;

@FacadeService
public class StockService {

    private final StockStore stockStore;
    private final StockReader stockReader;

    public StockService(final StockStore stockStorage, final StockReader stockReader) {
        this.stockStore = stockStorage;
        this.stockReader = stockReader;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        final StockInfo stockInfo = stockReader.findById(id).decrease(quantity);
        stockStore.saveAndFlush(new StockCommand(stockInfo.id(), stockInfo.productId(), stockInfo.quantity()));
    }

    public synchronized void synchronizedDecrease(Long id, Long quantity) {
        final StockInfo stockInfo = stockReader.findById(id).decrease(quantity);
        stockStore.saveAndFlush(new StockCommand(stockInfo.id(), stockInfo.productId(), stockInfo.quantity()));
    }
}

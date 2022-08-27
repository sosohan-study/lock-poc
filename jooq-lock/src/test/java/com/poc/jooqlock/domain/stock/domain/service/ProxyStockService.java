package com.poc.jooqlock.domain.stock.domain.service;

public class ProxyStockService {

    private final StockService stockService;

    public ProxyStockService(final StockService stockService) {
        this.stockService = stockService;
    }

    public void synchronizedDecrease(Long id, Long quantity) {
        startTransaction();

        stockService.synchronizedDecrease(id, quantity);

        endTransaction();
    }

    public void startTransaction() {
    }

    public void endTransaction() {
    }
}

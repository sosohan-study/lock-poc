package com.poc.jpalock.domain.stock.domain.model;

import com.poc.jpalock.domain.stock.domain.persist.Stock;

public record StockInfo(
        Long id,
        Long productId,
        Long quantity
) {
    public StockInfo(final Stock stock) {
        this(stock.getId(), stock.getProductId(), stock.getQuantity());
    }
}

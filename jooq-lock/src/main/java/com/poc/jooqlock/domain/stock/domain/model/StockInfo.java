package com.poc.jooqlock.domain.stock.domain.model;

public record StockInfo(
        Long id,
        Long productId,
        Long quantity
) {
    private static final int MINIMUM_QUANTITY = 0;

    public StockInfo decrease(final Long quantity) {
        if (Math.subtractExact(this.quantity, quantity) < MINIMUM_QUANTITY) {
            throw new RuntimeException();
        }
        return new StockInfo(id, productId, Math.subtractExact(this.quantity, quantity));
    }
}

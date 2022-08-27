package com.poc.jooqlock.domain.stock.domain.model;

public record StockCommand(
        Long id,
        Long productId,
        Long quantity
) {
}

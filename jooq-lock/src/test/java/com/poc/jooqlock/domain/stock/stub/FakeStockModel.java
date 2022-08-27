package com.poc.jooqlock.domain.stock.stub;

import com.poc.jooqlock.domain.stock.domain.model.StockCommand;
import com.poc.jooqlock.domain.stock.domain.model.StockInfo;

public record FakeStockModel(
        Long id,
        Long productId,
        Long quantity
) {
    public StockInfo toInfo() {
        return new StockInfo(id, productId, quantity);
    }

    public StockCommand toCommand() {
        return new StockCommand(id, productId, quantity);
    }
}

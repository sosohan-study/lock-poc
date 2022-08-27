package com.poc.jooqlock.domain.stock.stub;

import com.poc.jooqlock.domain.stock.domain.model.StockInfo;
import com.poc.jooqlock.domain.stock.domain.reader.StockReader;

public class FakeStockReader implements StockReader, FakeStockDataStore {
    @Override
    public StockInfo findById(final Long id) {
        return store.get(id).toInfo();
    }
}

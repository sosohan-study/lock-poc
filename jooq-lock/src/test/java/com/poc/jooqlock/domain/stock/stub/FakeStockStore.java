package com.poc.jooqlock.domain.stock.stub;

import com.poc.jooqlock.domain.stock.domain.model.StockCommand;
import com.poc.jooqlock.domain.stock.domain.store.StockStore;

public class FakeStockStore implements StockStore, FakeStockDataStore {

    @Override
    public StockCommand saveAndFlush(final StockCommand stockCommand) {
        store.put(stockCommand.id(), new FakeStockModel(stockCommand.id(), stockCommand.productId(), stockCommand.quantity()));
        return stockCommand;
    }
}

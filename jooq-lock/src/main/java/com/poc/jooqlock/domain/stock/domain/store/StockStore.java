package com.poc.jooqlock.domain.stock.domain.store;

import com.poc.jooqlock.domain.stock.domain.model.StockCommand;
import com.poc.jooqlock.global.config.annotation.Store;

@Store
public interface StockStore {
    StockCommand saveAndFlush(final StockCommand stockCommand);
}

package com.poc.jpalock.domain.stock.domain.store;

import com.poc.jpalock.domain.stock.domain.persist.Stock;

public interface StockStore {
    Stock saveAndFlush(final Stock stock);
}

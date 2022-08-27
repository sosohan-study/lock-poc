package com.poc.jooqlock.domain.stock.domain.reader;

import com.poc.jooqlock.domain.stock.domain.model.StockInfo;
import com.poc.jooqlock.global.config.annotation.Reader;

@Reader
public interface StockReader {
    StockInfo findById(Long id);
}

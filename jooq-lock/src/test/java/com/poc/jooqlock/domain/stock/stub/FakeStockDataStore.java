package com.poc.jooqlock.domain.stock.stub;

import java.util.HashMap;
import java.util.Map;

public interface FakeStockDataStore {
    Map<Long, FakeStockModel> store = new HashMap<>();
}

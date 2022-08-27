package com.poc.jooqlock.global.mileage.stub;

import java.util.HashMap;
import java.util.Map;

public interface FakeMileageDataStore {
    Map<Long, FakeMileageModel> store = new HashMap<>();
}

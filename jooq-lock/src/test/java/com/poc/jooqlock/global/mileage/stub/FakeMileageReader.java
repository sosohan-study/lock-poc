package com.poc.jooqlock.global.mileage.stub;

import com.poc.jooqlock.domain.mileage.domain.model.MileageInfo;
import com.poc.jooqlock.domain.mileage.domain.reader.MileageReader;

public class FakeMileageReader implements MileageReader, FakeMileageDataStore {
    @Override
    public MileageInfo findById(final Long id) {
        return store.get(id).toInfo();
    }
}

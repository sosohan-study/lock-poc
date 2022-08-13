package com.poc.jooqlock.global.mileage.infrastructure.store;

import com.poc.jooqlock.global.config.annotation.Store;
import com.poc.jooqlock.global.mileage.domain.model.MileageCommand;
import com.poc.jooqlock.global.mileage.domain.store.MileageStore;

@Store
public class MileageStoreImpl implements MileageStore {
    @Override
    public MileageCommand saveAndFlush(final MileageCommand mileageCommand) {
        return null;
    }
}

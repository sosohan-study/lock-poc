package com.poc.jooqlock.global.mileage.domain.store;

import com.poc.jooqlock.global.config.annotation.Store;
import com.poc.jooqlock.global.mileage.domain.model.MileageCommand;

@Store
public interface MileageStore {
    MileageCommand saveAndFlush(final MileageCommand mileageCommand);
}

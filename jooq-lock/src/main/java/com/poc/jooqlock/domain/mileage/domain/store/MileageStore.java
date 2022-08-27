package com.poc.jooqlock.domain.mileage.domain.store;

import com.poc.jooqlock.domain.mileage.domain.model.MileageCommand;
import com.poc.jooqlock.global.config.annotation.Store;

@Store
public interface MileageStore {
    MileageCommand saveAndFlush(final MileageCommand mileageCommand);
}

package com.poc.jooqlock.global.mileage.stub;

import com.poc.jooqlock.domain.mileage.domain.model.MileageCommand;
import com.poc.jooqlock.domain.mileage.domain.store.MileageStore;

public class FakeMileageStore implements MileageStore, FakeMileageDataStore {

    @Override
    public MileageCommand saveAndFlush(final MileageCommand mileageCommand) {
        store.put(mileageCommand.id(), new FakeMileageModel(mileageCommand.id(), mileageCommand.point()));
        return mileageCommand;
    }
}

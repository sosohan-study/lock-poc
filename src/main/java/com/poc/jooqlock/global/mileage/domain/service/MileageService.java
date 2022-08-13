package com.poc.jooqlock.global.mileage.domain.service;

import com.poc.jooqlock.global.config.annotation.FacadeService;
import com.poc.jooqlock.global.mileage.domain.model.MileageCommand;
import com.poc.jooqlock.global.mileage.domain.model.MileageInfo;
import com.poc.jooqlock.global.mileage.domain.reader.MileageReader;
import com.poc.jooqlock.global.mileage.domain.store.MileageStore;

@FacadeService
public class MileageService {

    private final MileageReader mileageReader;
    private final MileageStore mileageStore;

    public MileageService(final MileageReader mileageReader, final MileageStore mileageStore) {
        this.mileageReader = mileageReader;
        this.mileageStore = mileageStore;
    }

    public void deduct(Long id, Long deductedPoint) {
        MileageInfo mileageInfo = mileageReader.findById(id);
        MileageInfo deductedMileageInfo = mileageInfo.deduct(deductedPoint);
        MileageCommand mileageCommand = new MileageCommand(deductedMileageInfo.id(), deductedMileageInfo.point());
        mileageStore.saveAndFlush(mileageCommand);
    }
}

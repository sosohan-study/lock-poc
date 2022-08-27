package com.poc.jooqlock.domain.mileage.domain.service;

import com.poc.jooqlock.domain.mileage.domain.model.MileageCommand;
import com.poc.jooqlock.domain.mileage.domain.model.MileageInfo;
import com.poc.jooqlock.domain.mileage.domain.reader.MileageReader;
import com.poc.jooqlock.domain.mileage.domain.store.MileageStore;
import com.poc.jooqlock.global.config.annotation.FacadeService;

import java.util.Optional;

@FacadeService
public class MileageService {

    private final MileageReader mileageReader;
    private final MileageStore mileageStore;

    public MileageService(final MileageReader mileageReader, final MileageStore mileageStore) {
        this.mileageReader = mileageReader;
        this.mileageStore = mileageStore;
    }

    public void accumulate(Long id, Long deductedPoint) {
        Optional.ofNullable(mileageReader.findById(id)).ifPresentOrElse(
                mileageInfo -> {
                    MileageInfo deductedMileageInfo = mileageInfo.accumulate(deductedPoint);
                    mileageStore.saveAndFlush(new MileageCommand(deductedMileageInfo.id(), deductedMileageInfo.point()));
                },
                () -> mileageStore.saveAndFlush(new MileageCommand(id, deductedPoint)));
    }

    public void deduct(Long id, Long deductedPoint) {
        MileageInfo mileageInfo = mileageReader.findById(id);
        MileageInfo deductedMileageInfo = mileageInfo.deduct(deductedPoint);
        mileageStore.saveAndFlush(new MileageCommand(deductedMileageInfo.id(), deductedMileageInfo.point()));
    }
}

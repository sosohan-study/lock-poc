package com.poc.jooqlock.global.mileage.stub;

import com.poc.jooqlock.domain.mileage.domain.model.MileageCommand;
import com.poc.jooqlock.domain.mileage.domain.model.MileageInfo;

public record FakeMileageModel(
        Long id,
        Long point
) {
    public MileageInfo toInfo() {
        return new MileageInfo(id, point);
    }

    public MileageCommand toCommand() {
        return new MileageCommand(id, point);
    }
}

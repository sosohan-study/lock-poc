package com.poc.jooqlock.global.mileage.domain.model;

public record MileageCommand(
        Long id,
        Long point
) {
    public MileageCommand deduct(final Long deductedPoint) {
        return new MileageCommand(id, Math.subtractExact(point, deductedPoint));
    }
}

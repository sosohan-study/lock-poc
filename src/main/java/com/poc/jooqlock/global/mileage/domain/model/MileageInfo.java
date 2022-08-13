package com.poc.jooqlock.global.mileage.domain.model;

public record MileageInfo(
        Long id,
        Long point
) {
    public MileageInfo deduct(final Long deductedPoint) {
        return new MileageInfo(id, Math.subtractExact(point, deductedPoint));
    }
}

package com.poc.jooqlock.domain.mileage.domain.model;

public record MileageInfo(
        Long id,
        Long point
) {
    public MileageInfo deduct(final Long deductedPoint) {
        return new MileageInfo(id, Math.subtractExact(point, deductedPoint));
    }

    public MileageInfo accumulate(final Long deductedPoint) {
        return new MileageInfo(id, Math.addExact(point, deductedPoint));
    }
}

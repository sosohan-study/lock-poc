package com.poc.jooqlock.domain.mileage.interfaces.dto;

public class MileageAccumulateRequest {

    private Long point;

    protected MileageAccumulateRequest() {
    }

    public MileageAccumulateRequest(final Long point) {
        this.point = point;
    }

    public Long getPoint() {
        return point;
    }
}

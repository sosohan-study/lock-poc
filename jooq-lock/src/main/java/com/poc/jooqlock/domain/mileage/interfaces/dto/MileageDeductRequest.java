package com.poc.jooqlock.domain.mileage.interfaces.dto;

public class MileageDeductRequest {

    private Long point;

    protected MileageDeductRequest() {
    }

    public MileageDeductRequest(final Long point) {
        this.point = point;
    }

    public Long getPoint() {
        return point;
    }
}

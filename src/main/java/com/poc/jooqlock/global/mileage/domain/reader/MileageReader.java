package com.poc.jooqlock.global.mileage.domain.reader;

import com.poc.jooqlock.global.config.annotation.Reader;
import com.poc.jooqlock.global.mileage.domain.model.MileageInfo;

@Reader
public interface MileageReader {
    MileageInfo findById(Long id);
}

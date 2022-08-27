package com.poc.jooqlock.domain.mileage.domain.reader;

import com.poc.jooqlock.domain.mileage.domain.model.MileageInfo;
import com.poc.jooqlock.global.config.annotation.Reader;

@Reader
public interface MileageReader {
    MileageInfo findById(Long id);
}

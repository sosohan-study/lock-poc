package com.poc.jooqlock.global.mileage.infrastructure.reader;

import com.poc.jooqlock.global.config.annotation.Reader;
import com.poc.jooqlock.global.mileage.domain.model.MileageInfo;
import com.poc.jooqlock.global.mileage.domain.reader.MileageReader;

@Reader
public class MileageReaderImpl implements MileageReader {
    @Override
    public MileageInfo findById(final Long id) {
        return null;
    }
}

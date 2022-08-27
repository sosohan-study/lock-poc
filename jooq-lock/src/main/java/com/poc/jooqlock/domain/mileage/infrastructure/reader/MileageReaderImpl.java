package com.poc.jooqlock.domain.mileage.infrastructure.reader;

import com.poc.jooqlock.domain.mileage.domain.model.MileageInfo;
import com.poc.jooqlock.domain.mileage.domain.reader.MileageReader;
import com.poc.jooqlock.global.config.annotation.Reader;
import jooq.dsl.tables.Mileage;
import jooq.dsl.tables.records.MileageRecord;
import org.jooq.DSLContext;

@Reader
public class MileageReaderImpl implements MileageReader {

    private final DSLContext dsl;

    public MileageReaderImpl(final DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public MileageInfo findById(final Long id) {
        final Mileage mileage = Mileage.MILEAGE;
        final MileageRecord mileageRecord = dsl.selectFrom(mileage)
                .where(mileage.ID.eq(id))
                .fetchOne();
        return new MileageInfo(mileageRecord.getId(), mileageRecord.getPoint());
    }
}

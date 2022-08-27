package com.poc.jooqlock.domain.mileage.infrastructure.store;

import com.poc.jooqlock.domain.mileage.domain.model.MileageCommand;
import com.poc.jooqlock.domain.mileage.domain.store.MileageStore;
import com.poc.jooqlock.global.config.annotation.Store;
import jooq.dsl.tables.Mileage;
import jooq.dsl.tables.records.MileageRecord;
import org.jooq.DSLContext;

@Store
public class MileageStoreImpl implements MileageStore {

    private final DSLContext dsl;

    public MileageStoreImpl(final DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public MileageCommand saveAndFlush(final MileageCommand mileageCommand) {
        final Mileage mileage = Mileage.MILEAGE;
        final MileageRecord mileageRecord = dsl.insertInto(mileage, mileage.POINT)
                .values(mileageCommand.point())
                .onDuplicateKeyIgnore()
                .returning(mileage.ID, mileage.POINT)
                .fetchOne();
        return new MileageCommand(mileageRecord.getId(), mileageRecord.getPoint());
    }
}

package com.poc.jooqlock.global.mileage.domain.service;

import com.poc.jooqlock.global.mileage.stub.FakeMileageDataStore;
import com.poc.jooqlock.global.mileage.stub.FakeMileageModel;
import com.poc.jooqlock.global.mileage.stub.FakeMileageReader;
import com.poc.jooqlock.global.mileage.stub.FakeMileageStore;
import com.poc.jooqlock.domain.mileage.domain.service.MileageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("마일리지 서비스 테스트")
class MileageServiceTest {

    private MileageService mileageService;

    @BeforeEach
    void setUp() {
        mileageService = new MileageService(new FakeMileageReader(), new FakeMileageStore());
        FakeMileageDataStore.store.clear();
    }

    @Nested
    class deduct_메소드는 {

        @Nested
        class 감소하고자_하는_마일리지가_입력되었을_경우 {

            @Test
            void 마일리지_감소가_진행된다() {
                // given
                final Long targetId = 1L;
                final Long deductedPoint = 1L;

                // when
                FakeMileageDataStore.store.put(1L, new FakeMileageModel(1L, Long.MAX_VALUE));
                mileageService.deduct(1L, 1L);

                // then
                final FakeMileageModel storedData = FakeMileageDataStore.store.get(targetId);
                assertThat(storedData.point()).isEqualTo(Long.MAX_VALUE - deductedPoint);
            }
        }
    }
}
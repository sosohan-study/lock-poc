package com.poc.jooqlock.domain.stock.domain.service;

import com.poc.jooqlock.domain.stock.stub.FakeStockModel;
import com.poc.jooqlock.domain.stock.stub.FakeStockReader;
import com.poc.jooqlock.domain.stock.stub.FakeStockStore;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품 재고 서비스 테스트")
class StockServiceTest {

    private StockService stockService;

    @BeforeEach
    void setUp() {
        stockService = new StockService(new FakeStockStore(), new FakeStockReader());
    }

    @AfterEach
    void tearDown() {
        FakeStockStore.store.clear();
    }

    @Nested
    class decrease_메소드는 {

        @Nested
        class 감소하고자_하는_상품의_개수가_입력되었을_경우 {

            @Test
            void 재고_감소가_진행된다() {
                // given
                final Long targetId = 1L;
                final Long decreasedQuantity = 1L;

                // when
                FakeStockStore.store.put(1L, new FakeStockModel(1L, 1L, Long.MAX_VALUE));
                stockService.decrease(1L, decreasedQuantity);

                // then
                final FakeStockModel stockModel = FakeStockStore.store.get(targetId);
                assertThat(stockModel.quantity()).isEqualTo(Long.MAX_VALUE - decreasedQuantity);
            }
        }
    }
}

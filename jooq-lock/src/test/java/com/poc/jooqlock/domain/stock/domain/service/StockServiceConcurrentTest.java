package com.poc.jooqlock.domain.stock.domain.service;

import com.poc.jooqlock.domain.stock.stub.FakeStockModel;
import com.poc.jooqlock.domain.stock.stub.FakeStockReader;
import com.poc.jooqlock.domain.stock.stub.FakeStockStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품 재고 서비스 테스트")
class StockServiceConcurrentTest {

    private StockService stockService;

    @BeforeEach
    void setUp() {
        stockService = new StockService(new FakeStockStore(), new FakeStockReader());
        FakeStockStore.store.put(1L, new FakeStockModel(1L, 1L, 100L));
    }

    @AfterEach
    void tearDown() {
        FakeStockStore.store.clear();
    }

    @Test
    void stock_decrease() {
        stockService.decrease(1L, 1L);
        final FakeStockModel fakeStockModel = FakeStockStore.store.get(1L);

        assertThat(fakeStockModel.quantity()).isEqualTo(99L);
    }

    @Test
    void stock_decrease_concurrent() throws InterruptedException {
        final int threadCounts = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCounts);

        for (int i = 0; i < threadCounts; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(1L, 1L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        final FakeStockModel fakeStockModel = FakeStockStore.store.get(1L);

        assertThat(fakeStockModel.quantity()).isGreaterThanOrEqualTo(0L);
    }

    @Test
    void stock_decrease_concurrent_solution_synchronize() throws InterruptedException {
        final int threadCounts = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCounts);

        for (int i = 0; i < threadCounts; i++) {
            executorService.submit(() -> {
                try {
                    stockService.synchronizedDecrease(1L, 1L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        final FakeStockModel fakeStockModel = FakeStockStore.store.get(1L);

        assertThat(fakeStockModel.quantity()).isEqualTo(0L);
    }

    @Test
    void stock_decrease_concurrent_solution_synchronize_with_transactional() throws InterruptedException {
        ProxyStockService proxyStockService = new ProxyStockService(stockService);
        final int threadCounts = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch = new CountDownLatch(threadCounts);

        for (int i = 0; i < threadCounts; i++) {
            executorService.submit(() -> {
                try {
                    proxyStockService.synchronizedDecrease(1L, 1L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        final FakeStockModel fakeStockModel = FakeStockStore.store.get(1L);

        assertThat(fakeStockModel.quantity()).isEqualTo(0L);
    }
}

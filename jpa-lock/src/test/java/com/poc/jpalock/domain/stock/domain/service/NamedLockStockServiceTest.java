package com.poc.jpalock.domain.stock.domain.service;

import com.poc.jpalock.domain.stock.domain.model.StockCommand;
import com.poc.jpalock.domain.stock.domain.persist.Stock;
import com.poc.jpalock.domain.stock.domain.persist.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("재고 관리 기능 테스트(네임드락)")
class NamedLockStockServiceTest {

    @Autowired
    private NamedLockStockService namedLockStockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        final Stock stock = new Stock(1L, 1L, 100L);
        stockRepository.saveAndFlush(stock);
    }

    @Nested
    class decrease_메소드는 {

        @Test
        void decreaseWithNamedLock_메서드는_재고_감소가_잘_진행된다() throws InterruptedException {
            final int threadCounts = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch countDownLatch = new CountDownLatch(threadCounts);
            for (int i = 0; i < threadCounts; i++) {
                executorService.submit(() -> {
                    try {
                        namedLockStockService.decreaseWithNamedLock(new StockCommand(1L, 1L, 1L));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
            final Stock stock = stockRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
            assertThat(stock.getQuantity()).isEqualTo(Math.subtractExact(100L, threadCounts));
            System.out.println(stock.getQuantity());
        }
    }
}

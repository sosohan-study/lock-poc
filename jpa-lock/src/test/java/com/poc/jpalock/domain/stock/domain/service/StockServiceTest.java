package com.poc.jpalock.domain.stock.domain.service;

import com.poc.jpalock.domain.stock.domain.model.StockCommand;
import com.poc.jpalock.domain.stock.domain.model.StockInfo;
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
@DisplayName("재고 관리 기능 테스트")
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        final Stock stock = new Stock(1L, 1L, 100L);
        stockRepository.saveAndFlush(stock);
    }

    @Nested
    class decrease_메소드는 {

        @Nested
        class 감소하고자_하는_상품의_개수가_입력되었을_경우 {

            @Test
            void 재고_감소가_진행된다() {
                // given
                final StockCommand stockCommand = new StockCommand(1L, 1L, 1L);

                // when
                final StockInfo stockInfo = stockService.decrease(stockCommand);

                // then
                assertThat(stockInfo.quantity()).isEqualTo(Math.subtractExact(100L, 1L));
            }
        }

        @Nested
        class 감소하고자_하는_상품의_개수가_동시에_입력되었을_경우 {

            @Test
            void synchronizedDecrease_메서드는_재고_감소가_잘_진행된다() throws InterruptedException {
                final int threadCounts = 100;
                ExecutorService executorService = Executors.newFixedThreadPool(32);
                CountDownLatch countDownLatch = new CountDownLatch(threadCounts);
                for (int i = 0; i < threadCounts; i++) {
                    executorService.submit(() -> {
                        try {
                            stockService.synchronizedDecrease(new StockCommand(1L, 1L, 1L));
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

            @Test
            void synchronizedTransactionalDecrease_메서드는_재고_감소가_잘_진행되지_않는다() throws InterruptedException {
                final int threadCounts = 100;
                ExecutorService executorService = Executors.newFixedThreadPool(32);
                CountDownLatch countDownLatch = new CountDownLatch(threadCounts);
                for (int i = 0; i < threadCounts; i++) {
                    executorService.submit(() -> {
                        try {
                            stockService.synchronizedTransactionalDecrease(new StockCommand(1L, 1L, 1L));
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                }
                countDownLatch.await();
                final Stock stock = stockRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
                assertThat(stock.getQuantity()).isGreaterThanOrEqualTo(Math.subtractExact(100L, threadCounts));
                System.out.println(stock.getQuantity());
            }
        }

        @Test
        void decreaseWithPessimisticLock_메서드는_재고_감소가_잘_진행된다() throws InterruptedException {
            final int threadCounts = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch countDownLatch = new CountDownLatch(threadCounts);
            for (int i = 0; i < threadCounts; i++) {
                executorService.submit(() -> {
                    try {
                        stockService.decreaseWithPessimisticLock(new StockCommand(1L, 1L, 1L));
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

    @Test
    void decreaseWithOptimisticLock_메서드는_재고_감소가_잘_진행된다() throws InterruptedException {
        final int threadCounts = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCounts);
        for (int i = 0; i < threadCounts; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decreaseWithOptimisticLock(new StockCommand(1L, 1L, 1L));
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
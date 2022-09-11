package com.poc.jpalock.domain.stock.domain.service;

import com.poc.jpalock.domain.stock.domain.model.StockCommand;
import com.poc.jpalock.domain.stock.domain.model.StockInfo;
import com.poc.jpalock.domain.stock.domain.persist.Stock;
import com.poc.jpalock.domain.stock.domain.reader.StockReader;
import com.poc.jpalock.domain.stock.domain.store.StockStore;
import com.poc.jpalock.global.annotation.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@FacadeService
public class StockService {

    private final StockStore stockStore;
    private final StockReader stockReader;

    @Transactional
    public StockInfo decrease(final StockCommand stockCommand) {
        final Stock stock = stockReader.findById(stockCommand.id())
                .decrease(stockCommand.quantity());
        return new StockInfo(stockStore.saveAndFlush(stock));
    }

    public synchronized StockInfo synchronizedDecrease(final StockCommand stockCommand) {
        final Stock stock = stockReader.findById(stockCommand.id())
                .decrease(stockCommand.quantity());
        return new StockInfo(stockStore.saveAndFlush(stock));
    }

    @Transactional
    public synchronized StockInfo synchronizedTransactionalDecrease(final StockCommand stockCommand) {
        final Stock stock = stockReader.findById(stockCommand.id())
                .decrease(stockCommand.quantity());
        return new StockInfo(stockStore.saveAndFlush(stock));
    }

    @Transactional
    public StockInfo decreaseWithPessimisticLock(final StockCommand stockCommand) {
        final Stock stock = stockReader.findByIdWithPessimisticLock(stockCommand.id())
                .decrease(stockCommand.quantity());
        return new StockInfo(stockStore.saveAndFlush(stock));
    }

    @Transactional
    public StockInfo decreaseWithOptimisticLock(final StockCommand stockCommand) {
        final Stock stock = stockReader.findByIdWithOptimisticLock(stockCommand.id())
                .decrease(stockCommand.quantity());
        return new StockInfo(stockStore.saveAndFlush(stock));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StockInfo decreaseWithNamedLock(final StockCommand stockCommand) {
        final Stock stock = stockReader.findByIdWithOptimisticLock(stockCommand.id())
                .decrease(stockCommand.quantity());
        return new StockInfo(stockStore.saveAndFlush(stock));
    }
}

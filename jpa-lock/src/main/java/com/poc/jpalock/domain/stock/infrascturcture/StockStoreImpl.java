package com.poc.jpalock.domain.stock.infrascturcture;

import com.poc.jpalock.domain.stock.domain.persist.Stock;
import com.poc.jpalock.domain.stock.domain.persist.StockRepository;
import com.poc.jpalock.domain.stock.domain.store.StockStore;
import com.poc.jpalock.global.annotation.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Store
public class StockStoreImpl implements StockStore {

    private final StockRepository stockRepository;

    @Override
    public Stock saveAndFlush(final Stock stock) {
        return stockRepository.saveAndFlush(stock);
    }
}

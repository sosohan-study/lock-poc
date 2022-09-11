package com.poc.jpalock.domain.stock.infrascturcture;

import com.poc.jpalock.domain.stock.domain.persist.Stock;
import com.poc.jpalock.domain.stock.domain.persist.StockRepository;
import com.poc.jpalock.domain.stock.domain.reader.StockReader;
import com.poc.jpalock.global.annotation.Reader;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Reader
public class StockReaderImpl implements StockReader {

    private final StockRepository stockRepository;

    @Override
    public Stock findById(final Long id) {
        return stockRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Stock findByIdWithPessimisticLock(final Long id) {
        return stockRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Stock findByIdWithOptimisticLock(final Long id) {
        return stockRepository.findByIdWithOptimisticLock(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}

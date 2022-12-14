package com.poc.jpalock.domain.stock.domain.reader;

import com.poc.jpalock.domain.stock.domain.persist.Stock;

public interface StockReader {
    Stock findById(final Long id);

    Stock findByIdWithPessimisticLock(final Long id);

    Stock findByIdWithOptimisticLock(final Long id);
}

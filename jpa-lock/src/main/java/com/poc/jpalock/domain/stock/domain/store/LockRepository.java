package com.poc.jpalock.domain.stock.domain.store;

import com.poc.jpalock.domain.stock.domain.persist.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LockRepository extends JpaRepository<Stock, Long> {
    @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
    void getLock(final String key);

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    void releaseLock(final String key);
}

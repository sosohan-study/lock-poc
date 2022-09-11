package com.poc.jpalock.domain.stock.domain.service;

import com.poc.jpalock.domain.stock.domain.model.StockCommand;
import com.poc.jpalock.domain.stock.domain.model.StockInfo;
import com.poc.jpalock.domain.stock.domain.persist.LockRepository;
import com.poc.jpalock.global.annotation.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

// 이름을 가진 메타데이터 락, 트랜잭션 종료시 자동 해제되지 않으므로 명확히 해제 or 선점 시간 지나야함
// 이전에는 stock 테이블의 행으로 락을 걸었지만, 네임드 락은 별도의 공간에서 락을 진행
// JPA Native 쿼리 사용, 동일 데이터 소스(실무에서는 분리 추천 -> 커넥션 풀이 부족해짐)
@RequiredArgsConstructor
@FacadeService
public class NamedLockStockService {

    private final LockRepository lockRepository;

    private final StockService stockService;

    @Transactional
    public StockInfo decreaseWithNamedLock(final StockCommand stockCommand) throws InterruptedException {
        try {
            lockRepository.getLock(stockCommand.id().toString());
            return stockService.decreaseWithNamedLock(stockCommand);
        } finally {
            lockRepository.releaseLock(stockCommand.id().toString());
        }
    }
}

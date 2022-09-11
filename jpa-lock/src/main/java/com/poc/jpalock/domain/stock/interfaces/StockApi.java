package com.poc.jpalock.domain.stock.interfaces;

import com.poc.jpalock.domain.stock.domain.model.StockInfo;
import com.poc.jpalock.domain.stock.domain.service.StockService;
import com.poc.jpalock.domain.stock.interfaces.dto.StockDecrease;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/stock")
@RestController
public class StockApi {

    private final StockService stockService;

    @PostMapping("/decrease")
    public ResponseEntity<?> decrease(@RequestBody final StockDecrease.Request request) {
        final StockInfo stockInfo = stockService.decrease(request.toCommand());
        return ResponseEntity.status(HttpStatus.OK).body(new StockDecrease.Response(stockInfo));
    }
}

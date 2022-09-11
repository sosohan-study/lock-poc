package com.poc.jpalock.domain.stock.interfaces.dto;

import com.poc.jpalock.domain.stock.domain.model.StockCommand;
import com.poc.jpalock.domain.stock.domain.model.StockInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StockDecrease {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        private Long id;
        private Long quantity;

        public StockCommand toCommand() {
            return new StockCommand(id, null, quantity);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private Long id;
        private Long productId;
        private Long quantity;

        public Response(final StockInfo stockInfo) {
            this.id = stockInfo.id();
            this.productId = stockInfo.productId();
            this.quantity = stockInfo.quantity();
        }
    }
}

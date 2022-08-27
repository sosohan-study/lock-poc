package com.poc.jpalock.domain.stock.domain.persist;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@Table(name = "stocks")
@Entity
public class Stock {

    private static final int MINIMUM_QUANTITY = 0;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long productId;

    @Column
    private Long quantity;

    public Stock(final Long id, final Long productId, final Long quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Stock decrease(final Long quantity) {
        if (Math.subtractExact(this.quantity, quantity) < MINIMUM_QUANTITY) {
            throw new RuntimeException();
        }
        return new Stock(id, productId, Math.subtractExact(this.quantity, quantity));
    }
}

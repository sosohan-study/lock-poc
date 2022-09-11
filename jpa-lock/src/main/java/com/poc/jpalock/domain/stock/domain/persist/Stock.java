package com.poc.jpalock.domain.stock.domain.persist;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
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

    @Version
    private Long version;

    public Stock(final Long id, final Long productId, final Long quantity) {
        this(id, productId, quantity, 0L);
    }

    public Stock decrease(final Long quantity) {
        if (Math.subtractExact(this.quantity, quantity) < MINIMUM_QUANTITY) {
            throw new RuntimeException();
        }
        return new Stock(id, productId, Math.subtractExact(this.quantity, quantity), version);
    }
}

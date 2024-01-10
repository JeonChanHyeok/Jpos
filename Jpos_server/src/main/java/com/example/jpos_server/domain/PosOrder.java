package com.example.jpos_server.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@ToString
@Entity
public class PosOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String posOrderContent; // 주문 내용

    @Setter
    @Column(nullable = false)
    private int posOrderPrice; // 주문 가격

    @Setter
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store store; // 주문 가게

    @Setter
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Seat seat; // 주문 자리


    public PosOrder(String posOrderContent, int posOrderPrice, Store store, Seat seat) {
        this.posOrderContent = posOrderContent;
        this.posOrderPrice = posOrderPrice;
        this.store = store;
        this.seat = seat;
    }

    public PosOrder() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PosOrder that)) return false;
        return id != null && id.equals(that.id);
    }


}

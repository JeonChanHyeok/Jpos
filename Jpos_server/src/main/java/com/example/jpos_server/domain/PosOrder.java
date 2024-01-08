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

    @Setter
    @Column
    @ColumnDefault("0")
    private int state; // 주문 상태 (0: 주문 없음(기본 값), 1: 주문 진행중, 2: 완료된 주문, 3: 취소된 주문)

    public PosOrder(String posOrderContent, int posOrderPrice, Store store, Seat seat, int state) {
        this.posOrderContent = posOrderContent;
        this.posOrderPrice = posOrderPrice;
        this.store = store;
        this.seat = seat;
        this.state = state;
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

package com.example.jpos_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Getter
@ToString
@Entity
public class EndPosOrder extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store store; // 주문 가게

    @Setter
    @Column(nullable = false)
    private String seatName; // 주문 자리 이름

    @Setter
    @Column(nullable = false)
    private String posOrderContent; // 주문 내용

    @Setter
    @Column(nullable = false)
    private int posOrderPrice; // 주문 가격



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EndPosOrder that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

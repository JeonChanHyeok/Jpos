package com.example.jpos_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.Objects;

@Getter
@ToString
@Entity
@DynamicInsert
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String seatName; // 자리 이름

    @Setter
    @ManyToOne(optional = false)
    private Store store; // 가게

    @Setter
    @ColumnDefault("0")
    private int posUsing; // 포스기에서 사용중인지 확인하는 변수 0:사용안함 1:사용중

    public Seat() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Seat that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

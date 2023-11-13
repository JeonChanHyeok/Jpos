package com.example.jpos_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

import java.util.Objects;

@Getter
@ToString
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String storeName; // 가게 이름

    @Setter
    @Column(nullable = false)
    private Point storeLoc; // 가게 좌표

    private Double latitude; //가게 좌표 이용을 위한 위도

    private Double longitude; // 가게 좌표 이용을 위한 경도

    @Setter
    @ManyToOne(optional = false)
    private User user; // 가게 점주

    public Store() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Store that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

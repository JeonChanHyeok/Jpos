package com.example.jpos_server.domain;

import com.example.jpos_server.domain.User.Authority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private Double latitude; //가게 좌표 이용을 위한 위도

    @Setter
    @Column(nullable = false)
    private Double longitude; // 가게 좌표 이용을 위한 경도


    public Store(String storeName, Double latitude, Double longitude) {
        this.storeName = storeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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

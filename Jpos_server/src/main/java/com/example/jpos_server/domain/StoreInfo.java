package com.example.jpos_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@Entity
public class StoreInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne(optional = false)
    private Store store; // 가게

    @Setter
    @Column
    private String description; // 가게 설명

    @Setter
    @Column
    private String callNum; // 가게 전화 번호

    @Setter
    @Column
    private String location; // 가게 주소

    public StoreInfo(){
    }

    public StoreInfo(Store store, String description, String callNum, String location) {
        this.store = store;
        this.description = description;
        this.callNum = callNum;
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StoreInfo that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

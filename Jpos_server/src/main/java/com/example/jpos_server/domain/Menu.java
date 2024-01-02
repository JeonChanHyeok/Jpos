package com.example.jpos_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String menuName; // 메뉴 이름

    @Setter
    @ManyToOne(optional = false)
    private Store store; // 가게

    @Setter
    @ManyToOne(optional = false)
    private Category category; // 카테고리

    @Setter
    @Column(nullable = false)
    private int price; // 가격

    public Menu(String menuName, Store store, Category category, int price) {
        this.menuName = menuName;
        this.store = store;
        this.category = category;
        this.price = price;
    }

    public Menu() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Menu that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.example.jpos_server.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class IdempotencyKey {
    @Id
    private String id;
}

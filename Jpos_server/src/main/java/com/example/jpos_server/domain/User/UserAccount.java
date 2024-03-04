package com.example.jpos_server.domain.User;


import com.example.jpos_server.domain.Store;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String loginPw;

    @Column(nullable = false)
    private String userName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Authority> roles = new HashSet<>();

    @Setter
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store store;

    @Builder
    public UserAccount(String loginId, String loginPw, String userName, Set<Authority> roles, Store store) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.userName = userName;
        this.roles = roles;
        this.store = store;
    }
}

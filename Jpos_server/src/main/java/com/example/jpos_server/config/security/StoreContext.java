package com.example.jpos_server.config.security;


import com.example.jpos_server.domain.Store;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class StoreContext extends User {

    private final Store store;

    public StoreContext(Store store, Collection<? extends GrantedAuthority> authorities){
        super(store.getStoreLoginId(), store.getStoreLoginPw(), authorities);
        this.store = store;
    }

}

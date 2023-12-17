package com.example.jpos_server.dto.security;

import com.example.jpos_server.dto.StoreDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record JposPrincipal(
        String storeLoginId,
        String storeLoginPw,
        Collection<? extends GrantedAuthority> authorities,
        String storeName,
        Double storeLat,
        Double storeLon
) implements UserDetails {


    public static JposPrincipal of( String storeLoginId, String storeLoginPw, String storeName, Double storeLat, Double storeLon) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new JposPrincipal(
                storeLoginId,
                storeLoginPw,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                storeName,
                storeLat,
                storeLon
        );
    }

    public static JposPrincipal from(StoreDto dto){
        return JposPrincipal.of(
                dto.storeLoginId(),
                dto.storeLoginPw(),
                dto.storeName(),
                dto.latitude(),
                dto.longitude()
        );
    }

    public StoreDto toDto(){
        return StoreDto.of(
                storeName,
                storeLat,
                storeLon,
                storeLoginId,
                storeLoginPw
        );
    }

    @Override
    public String getUsername() {
        return storeLoginId;
    }

    @Override
    public String getPassword() {
        return storeLoginPw;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum RoleType{
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}

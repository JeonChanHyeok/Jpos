package com.example.jpos_server.config.security;

import com.example.jpos_server.domain.User.UserAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class UserAccountDetails implements UserDetails {

    private Long id;

    private String loginId;

    @JsonIgnore
    private String loginPw;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserAccountDetails build(UserAccount userAccount) {
        List<GrantedAuthority> authorityList = userAccount.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserAccountDetails(
                userAccount.getId(),
                userAccount.getLoginId(),
                userAccount.getLoginPw(),
                authorityList
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return loginPw;
    }

    @Override
    public String getUsername() {
        return loginId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserAccountDetails user = (UserAccountDetails) o;
        return Objects.equals(id, user.id);
    }
}

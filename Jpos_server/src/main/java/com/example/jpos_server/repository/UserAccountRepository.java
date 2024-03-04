package com.example.jpos_server.repository;

import com.example.jpos_server.domain.User.UserAccount;
import com.example.jpos_server.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByLoginId(String loginId);
    Boolean existsByLoginId(String loginId);
    List<UserAccount> findAllByStoreId(Long storeId);
    void deleteByLoginId(String loginId);
}
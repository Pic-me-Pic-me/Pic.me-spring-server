package com.with.picme.repository;

import com.with.picme.entity.AuthenticationProvider;
import com.with.picme.entity.ProviderType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface AuthenticationProviderRepository extends JpaRepository<AuthenticationProvider, Long> {
    @EntityGraph(attributePaths = "user")
    Optional<AuthenticationProvider> findByIdAndProvider(Long id, ProviderType providerType);
}
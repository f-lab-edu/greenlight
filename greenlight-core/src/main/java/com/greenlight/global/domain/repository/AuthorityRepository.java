package com.greenlight.global.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.greenlight.global.domain.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}

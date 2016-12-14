package com.fengyunjie.railway.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fengyunjie.railway.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}

package com.fengyunjie.railway.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fengyunjie.railway.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT a FROM User a where a.username = ?1")
	User findByUsername(String username);
}

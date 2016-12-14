package com.fengyunjie.railway.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fengyunjie.railway.auth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}

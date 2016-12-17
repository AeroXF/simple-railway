package com.fengyunjie.railway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fengyunjie.railway.model.Contacts;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {

	@Query
	List<Contacts> findByUserId(Long id);

	@Modifying
	@Query("DELETE FROM Contacts WHERE userId = ?1 AND username = ?2")
	void deleteByUsername(Long id, String username);

}

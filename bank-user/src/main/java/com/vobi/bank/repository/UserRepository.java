package com.vobi.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vobi.bank.domain.Users;

public interface UserRepository extends JpaRepository<Users, String>{
	List<Users> findByUserEmail(String userEmail);
	List<Users> findByEnable(String enable);
	List<Users> findByNameLike(String name);
}

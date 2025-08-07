package com.rahul.bugsage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rahul.bugsage.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByMobile(String mobile);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);

	boolean existsByMobile(String mobile);

}

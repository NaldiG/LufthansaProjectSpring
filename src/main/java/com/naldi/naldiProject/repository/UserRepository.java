package com.naldi.naldiProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naldi.naldiProject.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findById(Long id);

	List<User> findAll();

	List<User> findByUsernameContainingIgnoreCase(String username);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmailAndUsername(String email, String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}

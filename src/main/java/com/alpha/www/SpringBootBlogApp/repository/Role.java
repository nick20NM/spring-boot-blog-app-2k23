package com.alpha.www.SpringBootBlogApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Role extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
}

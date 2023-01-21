package com.alpha.www.SpringBootBlogApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.www.SpringBootBlogApp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
}

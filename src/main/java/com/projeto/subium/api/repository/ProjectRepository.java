package com.projeto.subium.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.subium.api.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	public Optional<Project> findByNameIgnoreCase(String name);

}

package com.projeto.subium.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projeto.subium.api.model.EmployeeProject;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
	@Query("SELECT COUNT(e) FROM EmployeeProject e WHERE e.emp_id=:id")
    Long numProjetosFuncionarios(@Param("id") int id);

}

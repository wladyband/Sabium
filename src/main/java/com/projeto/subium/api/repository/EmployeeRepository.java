package com.projeto.subium.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.subium.api.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

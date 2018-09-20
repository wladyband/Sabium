package com.projeto.subium.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projeto.subium.api.model.Employee;
import com.projeto.subium.api.repository.EmployeeRepository;

@Service
public class EmployeeServices {
	
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Employee atualizar(Long id, Employee employee) {
		Employee employeeSalva = employeeRepository.findOne(id);
		if (employeeSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(employee, employeeSalva, "id");
		return employeeRepository.save(employeeSalva);
	}
    

}

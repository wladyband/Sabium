package com.projeto.subium.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.subium.api.model.EmployeeProject;
import com.projeto.subium.api.repository.EmployeeProjectRepository;
import com.projeto.subium.api.service.exception.NameAtualizarProjectEmployeeCadastradoException;
import com.projeto.subium.api.service.exception.NameProjectEmployeeCadastradoException;

@Service
public class EmployeeProjectService {
	
	@Autowired
	private EmployeeProjectRepository employeeProjectRepository;
	
	
	public EmployeeProject salvar(EmployeeProject employeeProject) {
        if(employeeProjectRepository.numProjetosFuncionarios(employeeProject.getEmp_id()) < 2)
            return employeeProjectRepository.save(employeeProject);
        else
            throw new NameProjectEmployeeCadastradoException();
    }
	
	public EmployeeProject atualizar(Long id, EmployeeProject employeeProject) {
		
		EmployeeProject EmployeeprojectSalva = employeeProjectRepository.findOne(id);
		
		 if(employeeProjectRepository.numProjetosFuncionarios(employeeProject.getEmp_id()) < 2 || EmployeeprojectSalva == null ) {
			 
			 	BeanUtils.copyProperties(employeeProject, EmployeeprojectSalva, "id");
				return employeeProjectRepository.save(EmployeeprojectSalva);
				
        } else {
            throw new NameAtualizarProjectEmployeeCadastradoException();
        }
        
        
        
    }	
	
}

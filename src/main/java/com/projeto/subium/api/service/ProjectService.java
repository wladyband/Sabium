package com.projeto.subium.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projeto.subium.api.model.Project;
import com.projeto.subium.api.repository.ProjectRepository;
import com.projeto.subium.api.service.exception.NameProjectCadastradoException;

@Service
public class ProjectService {
	
	
	@Autowired
	private ProjectRepository projectRepository;

	public Project salvar(Project project) {
		Optional<Project> projectOptional = projectRepository.findByNameIgnoreCase(project.getName());
		if (projectOptional.isPresent()) {
			throw new NameProjectCadastradoException();
		}
		
		return projectRepository.save(project);
	}

	
	public Project atualizar(Long id, Project project) {
		Project projectSalva = projectRepository.findOne(id);
		if (projectSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(project, projectSalva, "id");
		return projectRepository.save(projectSalva);
	}

}

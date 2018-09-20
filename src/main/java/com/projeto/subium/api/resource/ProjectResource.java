package com.projeto.subium.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.subium.api.event.RecursoCriadoEvent;
import com.projeto.subium.api.exceptionhandler.SubiumExceptionHandler.Erro;
import com.projeto.subium.api.model.Project;
import com.projeto.subium.api.repository.ProjectRepository;
import com.projeto.subium.api.service.ProjectService;
import com.projeto.subium.api.service.exception.NameProjectCadastradoException;


@RestController
@RequestMapping("/project")
public class ProjectResource {

	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService projectService;
	
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Project> listar(){
		return projectRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Project> buscarPeloCodigo(@PathVariable Long id) {
		Project project = projectRepository.findOne(id);
		 return project != null ? ResponseEntity.ok(project) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Project> criar(@Valid @RequestBody Project project, HttpServletResponse response) {
		Project projectSalva = projectService.salvar(project);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, projectSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(projectSalva);
	}
	
	@ExceptionHandler({ NameProjectCadastradoException.class })
	public ResponseEntity<Object> handlerNameProjectCadastradoException(NameProjectCadastradoException ex){
		String mensagemUsuario = messageSource.getMessage("project.existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Project> atualizar(@PathVariable Long codigo, @Valid @RequestBody Project project) {
		Project projectSalva = projectService.atualizar(codigo, project);
		return ResponseEntity.ok(projectSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		projectRepository.delete(codigo);
	}
}

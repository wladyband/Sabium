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
import com.projeto.subium.api.model.EmployeeProject;
import com.projeto.subium.api.repository.EmployeeProjectRepository;
import com.projeto.subium.api.service.EmployeeProjectService;
import com.projeto.subium.api.service.exception.NameAtualizarProjectEmployeeCadastradoException;
import com.projeto.subium.api.service.exception.NameProjectEmployeeCadastradoException;


@RestController
@RequestMapping("/employeeproject")
public class EmployeeProjectResource {
	

	
	@Autowired
	private EmployeeProjectRepository employeeProjectRepository;
	
	@Autowired
	private EmployeeProjectService employeeProjectService;

	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	

	
	@GetMapping
	public List<EmployeeProject> listarEmpProj(){
		return employeeProjectRepository.findAll();
	}


	
	@PostMapping
	public ResponseEntity<EmployeeProject> criarEmpProj(@Valid @RequestBody EmployeeProject employeeProject, HttpServletResponse response) {
		EmployeeProject employeeProjectSalva = employeeProjectService.salvar(employeeProject);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, employeeProjectSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeProject);
	}
	
	@ExceptionHandler({ NameProjectEmployeeCadastradoException.class })
	public ResponseEntity<Object> handlerNameProjectEmployeeCadastradoException(NameProjectEmployeeCadastradoException ex){
		String mensagemUsuario = messageSource.getMessage("project.existente.funcionario", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({ NameAtualizarProjectEmployeeCadastradoException.class })
	public ResponseEntity<Object> handlerNameAtualizarProjectEmployeeCadastradoException(NameAtualizarProjectEmployeeCadastradoException ex){
		String mensagemUsuario = messageSource.getMessage("project.existente.funcionario", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	
	@PutMapping("/{codigo}")
	public ResponseEntity<EmployeeProject> atualizar(@PathVariable Long codigo, @Valid @RequestBody EmployeeProject employeeProject) {
		EmployeeProject employeeProjectSalva = employeeProjectService.atualizar(codigo, employeeProject);
		return ResponseEntity.ok(employeeProjectSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		employeeProjectRepository.delete(codigo);
	}
	
}




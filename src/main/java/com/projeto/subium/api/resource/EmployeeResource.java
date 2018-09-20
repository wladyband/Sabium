package com.projeto.subium.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.subium.api.event.RecursoCriadoEvent;
import com.projeto.subium.api.model.Employee;
import com.projeto.subium.api.repository.EmployeeRepository;
import com.projeto.subium.api.service.EmployeeServices;

@RestController
@RequestMapping("/employee")
public class EmployeeResource {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	@Autowired
	private EmployeeServices employeeServices;
	
	
	@GetMapping
	public List<Employee> listar(){
		return employeeRepository.findAll();
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<Employee> buscarPeloCodigo(@PathVariable Long id) {
		Employee employee = employeeRepository.findOne(id);
		 return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
	}
	
	
	@PostMapping
	public ResponseEntity<Employee> criar(@Valid @RequestBody Employee employee, HttpServletResponse response) {
		Employee employeeSalva = employeeRepository.save(employee);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, employeeSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeSalva);
	}
	
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Employee> atualizar(@PathVariable Long codigo, @Valid @RequestBody Employee employee) {
		Employee employeeSalva = employeeServices.atualizar(codigo, employee);
		return ResponseEntity.ok(employeeSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		employeeRepository.delete(codigo);
	}
	

}

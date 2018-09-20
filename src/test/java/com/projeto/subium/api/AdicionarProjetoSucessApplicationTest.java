package com.projeto.subium.api;



import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.projeto.subium.api.application.AppConfig;
import com.projeto.subium.api.model.Employee;
import com.projeto.subium.api.model.EmployeeProject;
import com.projeto.subium.api.model.Project;
import com.projeto.subium.api.repository.EmployeeRepository;
import com.projeto.subium.api.service.EmployeeProjectService;
import com.projeto.subium.api.service.ProjectService;
import com.projeto.subium.api.service.exception.NameProjectEmployeeCadastradoException;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(classes = {AppConfig.class})
public class AdicionarProjetoSucessApplicationTest {

	@Autowired
	private ProjectService projectService;
	
	
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	@Autowired
	private EmployeeProjectService employeeProjectService;

	
	@Test(expected = NameProjectEmployeeCadastradoException.class)
	public void testAdicionarProject() throws Exception{
		Project project1 = new Project("Google");
		this.projectService.salvar(project1);
		Assertions.assertThat(project1.getName()).isNotNull();
		
		Project project2 = new Project("TOTVS");
		this.projectService.salvar(project2);
		Assertions.assertThat(project2.getName()).isNotNull();
		
		Project project3 = new Project("Microsoft");
		this.projectService.salvar(project3);
		Assertions.assertThat(project3.getName()).isNotNull();
		
		Project project4 = new Project("TOVOS");
		this.projectService.salvar(project4);
		Assertions.assertThat(project4.getName()).isNotNull();
		
		
		Employee employee1 = new Employee("Douglas", BigDecimal.valueOf(16556));
		this.employeeRepository.save(employee1);
		Assertions.assertThat(employee1.getName()).isNotNull();
		Assertions.assertThat(employee1.getSalary()).isNotNull();
		
		Employee employee2 = new Employee("Paulo", BigDecimal.valueOf(16556));
		this.employeeRepository.save(employee2);
		Assertions.assertThat(employee2.getName()).isNotNull();
		Assertions.assertThat(employee2.getSalary()).isNotNull();
		
		
		Employee employee3 = new Employee("José", BigDecimal.valueOf(16556));
		this.employeeRepository.save(employee3);
		Assertions.assertThat(employee3.getName()).isNotNull();
		Assertions.assertThat(employee3.getSalary()).isNotNull();
		
		
		Employee employee4 = new Employee("Maria", BigDecimal.valueOf(16556));
		this.employeeRepository.save(employee4);
		Assertions.assertThat(employee4.getName()).isNotNull();
		Assertions.assertThat(employee4.getSalary()).isNotNull();
		
		
		EmployeeProject employeeProject1 = new EmployeeProject(1, 1);
		this.employeeProjectService.salvar(employeeProject1);
		Assertions.assertThat(employeeProject1.getEmp_id());
		Assertions.assertThat(employeeProject1.getProj_id());
		
		EmployeeProject employeeProject2 = new EmployeeProject(1, 1);
		this.employeeProjectService.salvar(employeeProject2);
		Assertions.assertThat(employeeProject2.getEmp_id());
		Assertions.assertThat(employeeProject2.getProj_id());
		
		
		EmployeeProject employeeProject3 = new EmployeeProject(1, 1);
		this.employeeProjectService.salvar(employeeProject3);
		Assertions.assertThat(employeeProject3.getEmp_id());
		Assertions.assertThat(employeeProject3.getProj_id());
		
		EmployeeProject employeeProject4 = new EmployeeProject(1, 1);
		this.employeeProjectService.salvar(employeeProject4);
		Assertions.assertThat(employeeProject4.getEmp_id());
		Assertions.assertThat(employeeProject4.getProj_id());
		
		
	}


	

	

}

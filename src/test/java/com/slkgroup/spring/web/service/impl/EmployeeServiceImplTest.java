package com.slkgroup.spring.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.slkgroup.spring.web.entity.Employee;
import com.slkgroup.spring.web.service.EmployeeService;

/**
 * @author Razi Ahmad & Sasmita Moharana
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/mvc-dispatcher-servlet.xml" })
@PropertySource({ "classpath:/application.properties" })
public class EmployeeServiceImplTest {

	@Autowired
	private EmployeeService employeeService;

	Employee employee = new Employee();

	@Test
	public void testingSeriveObject() {
		System.out.println(employeeService.hashCode());
	}

	@Before
	public void setUpBeforeEachTest() {
		employee.setName("Smith");
		employee.setAge(33);
		employee.setSalary(12000);
		employeeService.createEmployee(employee);
	}

	@Test
	public void testCreateEmployee() {
		Employee found = employeeService.getEmployee(employee.getId());
		assertEquals("Smith", found.getName());
	}

	@Test
	public void testUpdateEmployee() {
		employee.setName("baby");
		employeeService.updateEmployee(employee);
		Employee found = employeeService.getEmployee(employee.getId());
		assertEquals("baby", found.getName());
	}

	@Test
	public void testDeleteEmployee() {
		int size = employeeService.getAllEmployees().size();
		employeeService.deleteEmployee(employee.getId());
		assertTrue(size > employeeService.getAllEmployees().size());
	}

	@Test
	public void testGetEmployee() {
		Employee found = employeeService.getEmployee(employee.getId());
		assertEquals("Smith", found.getName());
	}

	@Test
	public void testGetAllEmployees() {
		int size = employeeService.getAllEmployees().size();
		employee.setId(1001);
		employee.setAge(32);
		employee.setName("Moharana");
		employee.setSalary(55000);
		employeeService.createEmployee(employee);
		assertTrue(size < employeeService.getAllEmployees().size());
	}

	@Test
	public void testGetAllEmployeesString() {

		List<Employee> search = employeeService.getAllEmployees(employee.getName());
		for (Employee emp111 : search) {
			System.out.println(emp111.getName());
			assertEquals("Smith", emp111.getName());
		}

	}

}

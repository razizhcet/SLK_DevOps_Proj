package com.slkgroup.spring.web.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.slkgroup.spring.web.dao.EmployeeDAO;
import com.slkgroup.spring.web.entity.Employee;

/**
 * @author Razi Ahmad & Sasmita Moharana
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/mvc-dispatcher-servlet.xml" })
@PropertySource({ "classpath:/application.properties" })
public class EmployeeDAOImplTest {

	@Autowired
	private EmployeeDAO employeeDAO;

	Employee employee = new Employee();

	

	@Before
	public void setUpBeforeEachTest() {
		employee.setName("Smith");
		employee.setAge(33);
		employee.setSalary(12000);
		employeeDAO.createEmployee(employee);
	}

	@Test
	public void testCreateEmployee_positive() {
		Employee found = employeeDAO.getEmployee(employee.getId());
		assertEquals("Smith", found.getName());
	}

	@Test
	public void testCreateEmployee_negative() {
		Employee found = employeeDAO.getEmployee(employee.getId());
		assertNotEquals("Smit", found.getName());
	}

	@Test
	public void testUpdateEmployee() {
		employee.setName("babi");
		employeeDAO.updateEmployee(employee);
		Employee found = employeeDAO.getEmployee(employee.getId());
		assertEquals("baby", found.getName());
	}

	@Test
	public void testDeleteEmployee() {
		int size = employeeDAO.getAllEmployees().size();
		employeeDAO.deleteEmployee(employee.getId());
		assertTrue(size > employeeDAO.getAllEmployees().size());
	}

	@Test
	public void testGetEmployee() {
		Employee available = employeeDAO.getEmployee(employee.getId());
		assertEquals(employee.getName(), available.getName());
	}

	@Test
	public void testGetAllEmployees() {
		int size = employeeDAO.getAllEmployees().size();
		employee.setId(1001);
		employee.setAge(32);
		employee.setName("Moharana");
		employee.setSalary(55000);
		employeeDAO.createEmployee(employee);
		assertTrue(size < employeeDAO.getAllEmployees().size());
	}

	@Test
	public void testGetAllEmployeesString() {

		List<Employee> search = employeeDAO.getAllEmployees(employee.getName());
		for (Employee emp111 : search) {
			System.out.println(emp111.getName());
			assertEquals("Smith", emp111.getName());

		}

	}

}

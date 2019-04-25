package com.slkgroup.spring.web.dao.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.slkgroup.spring.web.dao.EmployeeDAO;
import com.slkgroup.spring.web.entity.Employee;
import com.slkgroup.spring.web.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/mvc-dispatcher-servlet.xml" })
@PropertySource({ "classpath:/application.properties" })
public class EmployeeDAOImplTest {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeDAO employeeDAO;
	
	Employee employee = new Employee();

	@Test
	public void testingDAOObject() {
		System.out.println(employeeDAO.hashCode());
	}
	
	@Before
	public void setUpBeforeEachTest() {
		
		employee.setId(11);
		employee.setName("Smith");
		employee.setAge(30);
		employee.setSalary(12000);
	}

	@Test
	public void testCreateEmployee() {
		employeeDAO.createEmployee(employee);
		assertEquals("Smith", employee.getName());
	}
	@Test
	public void testUpdateEmployee() {
		employeeDAO.createEmployee(employee);
		employee.setName("baby");
		employeeDAO.updateEmployee(employee);
		assertEquals("baby", employee.getName());
	}
	@Test
	public void testDeleteEmployee() {
		
		employeeDAO.createEmployee(employee);
		int size = employeeDAO.getAllEmployees().size();
		employeeDAO.deleteEmployee(employee.getId());
		assertTrue(size > employeeDAO.getAllEmployees().size());
	}
    @Test
    public void testGetEmployee() {
    	employeeDAO.createEmployee(employee);
    	employeeDAO.getEmployee(employee.getId());
           assertEquals("Smith", employee.getName());
    }
    @Test
    public void testGetAllEmployees() {
    	employeeDAO.createEmployee(employee);
    	employeeDAO.getAllEmployees(employee.getName());
           assertEquals("Smith", employee.getName());
    }
    @Test
    public void testGetAllEmployeesString() {
    	employeeDAO.createEmployee(employee);
    	employeeDAO.getAllEmployees(employee.getName());
           assertEquals("Smith", employee.getName());
    }

}

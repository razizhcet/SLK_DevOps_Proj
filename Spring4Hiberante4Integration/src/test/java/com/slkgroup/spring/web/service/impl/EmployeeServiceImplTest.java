package com.slkgroup.spring.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.hibernate.annotations.SelectBeforeUpdate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
		
		employee.setId(11);
		employee.setName("Smith");
		employee.setAge(30);
		employee.setSalary(12000);
	}

	@Test
	public void testCreateEmployee() {
		employeeService.createEmployee(employee);
		assertEquals("Smith", employee.getName());
	}
	@Test
	public void testUpdateEmployee() {
		employeeService.createEmployee(employee);
		employee.setName("baby");
		employeeService.updateEmployee(employee);
		assertEquals("baby", employee.getName());
	}
	@Test
	public void testDeleteEmployee() {
		
		employeeService.createEmployee(employee);
		int size = employeeService.getAllEmployees().size();
		employeeService.deleteEmployee(employee.getId());
		assertTrue(size > employeeService.getAllEmployees().size());
	}
    @Test
    public void testGetEmployee() {
          employeeService.createEmployee(employee);
          employeeService.getEmployee(employee.getId());
           assertEquals("Smith", employee.getName());
    }
    @Test
    public void testGetAllEmployees() {
          employeeService.createEmployee(employee);
          employeeService.getAllEmployees(employee.getName());
           assertEquals("Smith", employee.getName());
    }
    @Test
    public void testGetAllEmployeesString() {
          employeeService.createEmployee(employee);
          employeeService.getAllEmployees(employee.getName());
           assertEquals("Smith", employee.getName());
    }

}

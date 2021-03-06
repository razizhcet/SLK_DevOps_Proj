package com.slkgroup.spring.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import com.slkgroup.spring.web.entity.Employee;
import com.slkgroup.spring.web.service.EmployeeService;

/**
 * @author Razi Ahmad & Sasmita Moharana
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/mvc-dispatcher-servlet.xml" })
@PropertySource({ "classpath:/application.properties" })
public class EmployeeControllerTest {

	@Autowired
	EmployeeController controller;

	@Autowired
	EmployeeService employeeService;

	Employee employee = new Employee();

	@Before
	public void setupBeforEachTest() {
		employee.setName("Smith");
		employee.setAge(30);
		employee.setSalary(12000);
	}

	@Test
	public void testHomePage() {
		ModelAndView mav = controller.homePage();
		Assert.assertEquals("home", mav.getViewName());
	}

	@Test
	public void testCreateEmployee() {
		ModelAndView mav = controller.createEmployee(employee);
		Assert.assertEquals("employeeForm", mav.getViewName());
	}

	@Test
	public void testEditEmployee() {
		ModelAndView mav = controller.editEmployee(employee.getId(), employee);
		Assert.assertEquals("employeeForm", mav.getViewName());
	}

	@Test
	public void testSaveEmployee() {
		employeeService.createEmployee(employee);
		ModelAndView mav = controller.saveEmployee(employee);
		Assert.assertEquals("redirect:getAllEmployees", mav.getViewName());
	}

	@Test
	public void testDeleteEmployee() {
		employeeService.createEmployee(employee);
		ModelAndView mav = controller.deleteEmployee(employee.getId());
		Assert.assertEquals("redirect:getAllEmployees", mav.getViewName());
	}

	@Test
	public void testGetAllEmployees() {
		ModelAndView mav = controller.getAllEmployees();
		Assert.assertEquals("employeeList", mav.getViewName());
	}

	@Test
	public void testSearchEmployee() {
		ModelAndView mav = controller.searchEmployee(employee.getName());
		Assert.assertEquals("employeeList", mav.getViewName());
	}

}

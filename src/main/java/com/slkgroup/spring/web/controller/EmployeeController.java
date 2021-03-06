package com.slkgroup.spring.web.controller;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.slkgroup.spring.web.entity.Employee;
import com.slkgroup.spring.web.service.EmployeeService;
/**
 * @author Balasubramanyam B
 * @version 1.0
 */
@Controller
public class EmployeeController {

	private static final Logger logger = Logger.getLogger(EmployeeController.class);

	public EmployeeController() {
		System.out.println("EmployeeController()");
	}

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = { "/", "/home" })
	public ModelAndView homePage() {
		return new ModelAndView("home");
	}

	@RequestMapping("createEmployee")
	public ModelAndView createEmployee(@ModelAttribute Employee employee) {
		return new ModelAndView("employeeForm");
	}

	@RequestMapping("editEmployee")
	public ModelAndView editEmployee(@RequestParam long id, @ModelAttribute Employee employee) {
		employee = employeeService.getEmployee(id);
		return new ModelAndView("employeeForm", "employeeObject", employee);
	}

	@RequestMapping("saveEmployee")
	public ModelAndView saveEmployee(@ModelAttribute Employee employee) {
		if (employee.getId() == 0) { // if employee id is 0 then creating the employee other updating the employee
			employeeService.createEmployee(employee);
		} else {
			employeeService.updateEmployee(employee);
		}
		return new ModelAndView("redirect:getAllEmployees");
	}

	@RequestMapping("deleteEmployee")
	public ModelAndView deleteEmployee(@RequestParam long id) {
		employeeService.deleteEmployee(id);
		return new ModelAndView("redirect:getAllEmployees");
	}

	@RequestMapping(value = { "getAllEmployees" })
	public ModelAndView getAllEmployees() {
		List<Employee> employeeList = employeeService.getAllEmployees();
		return new ModelAndView("employeeList", "employeeList", employeeList);
	}

	@RequestMapping("searchEmployee")
	public ModelAndView searchEmployee(@RequestParam("searchName") String searchName) {
		List<Employee> employeeList = employeeService.getAllEmployees(searchName);
		return new ModelAndView("employeeList", "employeeList", employeeList);
	}
}
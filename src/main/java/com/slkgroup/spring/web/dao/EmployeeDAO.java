/**
 * 
 */
package com.slkgroup.spring.web.dao;

import java.util.List;

import com.slkgroup.spring.web.entity.Employee;

/**
 * @author Balasubramanyam B
 * @version 1.0
 */
public interface EmployeeDAO {
	
	public long createEmployee(Employee employee);

	public Employee updateEmployee(Employee employee);

	public void deleteEmployee(long id);

	public List<Employee> getAllEmployees();

	public Employee getEmployee(long id);

	public List<Employee> getAllEmployees(String employeeName);
}

/**
 * 
 */
package com.slkgroup.spring.web.dao.impl;

import com.slkgroup.spring.web.dao.EmployeeDAO;
import com.slkgroup.spring.web.entity.Employee;
import com.slkgroup.spring.web.util.HibernateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Razi Ahmad & Sasmita Moharana
 * @version 1.0
 */

@Repository
@Transactional
public class EmployeeDAOImpl implements EmployeeDAO {

	public EmployeeDAOImpl() {
		System.out.println("EmployeeDAOImpl");
	}

	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public long createEmployee(Employee employee) {
		return (Long) hibernateUtil.create(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		return hibernateUtil.update(employee);
	}

	@Override
	public void deleteEmployee(long id) {
		Employee employee = new Employee();
		employee.setId(id);
		hibernateUtil.delete(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return hibernateUtil.fetchAll(Employee.class);
	}

	@Override
	public Employee getEmployee(long id) {
		return hibernateUtil.fetchById(id, Employee.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getAllEmployees(String employeeName) {
		String query = "SELECT e.* FROM Employee e WHERE e.name like '%" + employeeName.trim() + "%'";
		List<Object[]> employeeObjects = hibernateUtil.fetchAll(query);
		List<Employee> employees = new ArrayList<Employee>();
		for (Object[] employeeObject : employeeObjects) {
			Employee employee = new Employee();
			long id = Integer.parseInt(employeeObject[0].toString());
			int age = Integer.parseInt(employeeObject[1].toString());
			String name = (String) employeeObject[2];
			float salary = Float.parseFloat(employeeObject[3].toString());
			employee.setId(id);
			employee.setName(name);
			employee.setAge(age);
			employee.setSalary(salary);
			employees.add(employee);
		}
		System.out.println(employees);
		return employees;
	}
}
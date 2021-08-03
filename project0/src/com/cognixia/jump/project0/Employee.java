/**
 * 
 */
package com.cognixia.jump.project0;

import java.io.Serializable;
import java.util.Random;

import com.cognixia.jump.project0.Company.Department;

/**
 * @author Noah Fryer
 * 
 * Represents an individual record in the company employee log. 
 * Contains fields for employee information and methods to access 
 * and mutate those fields.
 *
 */
public class Employee implements Serializable, Comparable<Employee> {
	
	private static final long serialVersionUID = 10l;
	private int idNum;
	private String firstName;
	private String lastName;
	private Department department;
	private int salary;
	
	/**
	 * Default Constructor
	 */
	public Employee() {
		this.firstName = "Default";
		this.lastName = "Default";
		this.idNum = (new Random()).nextInt(100000);
		this.department = Department.DEFAULT;
		this.salary = 0;
	}
	
	/**
	 * Instantiates an Employee with the given ID
	 * @param id
	 */
	public Employee(int id) {
		this.firstName = "Default";
		this.lastName = "Default";
		this.idNum = id;
		this.department = Department.DEFAULT;
		this.salary = 0;
	}
	
	/**
	 * Instantiates a new object with the given information in
	 * the appropriate fields.
	 * @param firstName
	 * @param lastName
	 * @param idNum
	 * @param department
	 * @param salary
	 */
	public Employee(String firstName, String lastName, 
			int idNum, Department department, int salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNum = idNum;
		this.department = department;
		this.salary = salary;
	}
	
	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public int getID() {
		return this.idNum;
	}
	
	public Department getDepartment() {
		return this.department;
	}
	
	public int getSalary() {
		return this.salary;
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public void setLastName(String name) {
		this.lastName = name;
	}
	
	public void setID(int id) {
		this.idNum = id;
	}
	
	public void setDepartment(Department d) {
		this.department = d;
	}
	
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return this.firstName + ","
				+ this.lastName + ","
				+ this.idNum + ","
				+ this.department + ","
				+ this.getSalary();
	}

	@Override
	public int compareTo(Employee o) {
		if (this.idNum == o.idNum) {
			return 0;
		} else if (this.idNum < o.idNum) {
			return -1;
		} else {
			return 1;
		}
	}
}

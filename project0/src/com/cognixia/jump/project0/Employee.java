/**
 * 
 */
package com.cognixia.jump.project0;

import java.io.Serializable;

import com.cognixia.jump.project0.Company.Department;

/**
 * @author noah
 *
 */
public class Employee implements Serializable {
	
	private static final long serialVersionUID = 10l;
	private static int idCounter = 0;
	private final int idNum;
	private transient int ssn;
	private String firstName;
	private String lastName;
	private Department department;
	private int salary;
	
	public Employee() {
		this.firstName = "Default";
		this.lastName = "Default";
		this.idNum = idCounter++;
		this.ssn = 0;
		this.department = Department.DEFAULT;
		this.salary = 0;
	}
	
	/**
	 * 
	 * @param firstName The employee's given name
	 * @param lastName The employee's family name (surname)
	 * @param ssn The employee's social security number
	 */
	public Employee(String firstName, String lastName, int ssn) {
		// TODO Auto-generated constructor stub
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.idNum = idCounter++;
		this.department = Department.DEFAULT;
		this.salary = 0;
	}
	
	/**
	 * 
	 * @param firstName The employee's given name
	 * @param lastName The employee's family name (surname)
	 * @param ssn The employee's social security number
	 */
	public Employee(String firstName, String lastName, int ssn, 
			Department department, int salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.idNum = idCounter++;
		this.department = department;
		this.salary = salary;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public int getSSN() {
		return this.ssn;
	}
	
	public int getIDNum() {
		return this.idNum;
	}
	
	public String getDepartment() {
		return this.department.toString();
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
	
	public static void setIDCounter(int count) {
		idCounter = count;
	}

}

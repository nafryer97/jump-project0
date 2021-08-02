/**
 * 
 */
package com.cognixia.jump.project0;

import java.io.Serializable;
import java.util.Hashtable;
import com.cognixia.jump.project0.Employee;

/**
 * @author Noah Fryer
 *
 */
public class Company implements Serializable {
	
	enum Department implements Serializable {
		INFORMATION_TECHNOLOGY,
		SALES,
		ACCOUNTING,
		PUBLIC_RELATIONS,
		MAINTENANCE,
		ENGINEERING,
		SECURITY,
		LOGISTICS,
		HUMAN_RESOURCES,
		DEFAULT;
		
		private static final long serialVersionUID = 10l;
		private int numEmployees;
		
		Department() {
			this.numEmployees = 0;
		}
		
		public int getNumEmployees() {
			return this.numEmployees;
		}
		
		public void setNumEmployees(int num) {
			this.numEmployees = num;
		}
		
		public void incrementEmployees() {
			++this.numEmployees;
		}
		
		public void decrementEmployees() {
			--this.numEmployees;
		}
	}
	
	private static final long serialVersionUID = 10l;
	private final Hashtable<Integer, Employee> employees;
	private final Department[] departments;

	/**
	 * Default Constructor
	 */
	public Company() {
		this.employees = new Hashtable<Integer, Employee>();
		this.departments = Department.values();
	}

	/**
	 * @param args command-line arguments. Not necessary for this program.
	 */
	public static void main(String[] args) {

	}

}

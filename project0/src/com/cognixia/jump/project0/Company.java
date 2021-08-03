/**
 * 
 */
package com.cognixia.jump.project0;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.cognixia.jump.project0.Employee;

/**
 * @author Noah Fryer
 * 
 * The Company class holds a set of all employee records and provides methods
 * to add, remove, and search for records. This class can be written to and from
 * a file.
 * 
 * This class also contains the main method for the program.
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
		
		/**
		 * Parses a department match from a given string.
		 * @param str
		 * @return the department that corresponds to the given string,
		 * or DEFAULT if no match is found.
		 */
		static public Department parseDepartment(String str) {
			if (str.equalsIgnoreCase("information technology")) {
				return INFORMATION_TECHNOLOGY;
			} else if (str.equalsIgnoreCase("sales")) {
				return SALES;
			} else if (str.equalsIgnoreCase("accounting")) {
				return ACCOUNTING;
			} else if (str.equalsIgnoreCase("public relations")) {
				return PUBLIC_RELATIONS;
			} else if (str.equalsIgnoreCase("maintenance")) {
				return MAINTENANCE;
			} else if (str.equalsIgnoreCase("engineering")) {
				return ENGINEERING;
			} else if (str.equalsIgnoreCase("security")) {
				return SECURITY;
			} else if (str.equalsIgnoreCase("logistics")) {
				return LOGISTICS;
			} else if (str.equalsIgnoreCase("human resources")) {
				return HUMAN_RESOURCES;
			} else {
				return DEFAULT;
			}
		}
	}
	
	private static final long serialVersionUID = 10l;
	private final LinkedHashSet<Employee> employees;
	private final Department[] departments;

	/**
	 * Default Constructor
	 */
	public Company() {
		this.employees = new LinkedHashSet<Employee>();
		this.departments = Department.values();
	}
	
	/**
	 * Adds a single employee entry
	 * @param e
	 */
	public void addEmployee(Employee e) {
		this.employees.add(e);
	}
	
	/**
	 * Adds new employee entries from the given set.
	 * 
	 * @param s
	 */
	public void addEmployees(Set<Employee> s) {
		this.employees.addAll(s);
	}
	
	/**
	 * Searches for an Employee object with a matching ID.
	 * 
	 * @param id
	 * @return an Employee object, or null if there are no matches
	 */
	public Employee searchID(int id) {
		for (Employee e: this.employees) {
			if (e.getID() == id) {
				return e;
			}
		}
		
		return null;
	}
	
	/**
	 * Removes an employee entry from memory.
	 * 
	 * @param id
	 */
	public void removeEmployee(int id) {
		for (Employee e: this.employees) {
			if (e.getID() == id) {
				this.employees.remove(e);
				break;
			}
		}
	}
	
	/**
	 * Searches for all employees with a given name, first or last
	 * 
	 * @param name
	 * @return an array containing any entries matched.
	 */
	public Object[] searchName(String name) {
		Object[] result = this.employees.stream().filter(e -> 
		e.getFirstName().equals(name)
		).toArray();
		
		return result;
	}
	
	/**
	 * Searches for all employees in given department
	 * 
	 * @param d
	 * @return an array containing any entries that matched.
	 */
	public Object[] searchDepartment(Department d) {
		Object[] result = this.employees.stream().filter(e -> 
		e.getDepartment() == d
		).toArray();
		
		return result;
	}
	
	/**
	 * Returns a reference to the final data structure holding 
	 * the employee information.
	 * 
	 * @return LinkedHashSet<Employee>
	 */
	public LinkedHashSet<Employee> getEmployeeSet() {
		return this.employees;
	}
	
	/**
	 * Writes employee information to an object file.
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void writeEmployeeObjectFile(String path) throws IOException {
		FileOutputStream file = new FileOutputStream(path);
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(this);
		out.close();
		file.close();
	}
	
	/**
	 * Reads employee information from an object file and adds it 
	 * to the current instance's memory.
	 * 
	 * @param path
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readEmployeeObjectFile(String path) throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(file);
		Company temp = (Company) in.readObject();
		this.employees.addAll(temp.getEmployeeSet());
		in.close();
		file.close();
	}
	
	/**
	 * Reads employee records from a text file. One employee per line,
	 * fields separated by commas.
	 * @param path
	 */
	public void readEmployeeTextFile(String path) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(path));
		
		for (String line : lines) {
			String[] tokens = line.split(",");
			this.employees.add(
					new Employee(
							tokens[0],
							tokens[1],
							Integer.parseInt(tokens[2]),
							Department.parseDepartment(tokens[3]),
							Integer.parseInt(tokens[4])
					));
		}
	}
	
	/**
	 * Writes all employees to a text file. Fields are separated by commas,
	 * one employee per line
	 * 
	 * @param path
	 */
	public void writeEmployeeTextFile(String path) throws IOException {
		FileWriter file = new FileWriter(path);
		BufferedWriter out = new BufferedWriter(file);
		for (Employee e : this.employees) {
			out.write(e.toString());
			out.newLine();
		}
		out.close();
		file.close();
	}
	
	/**
	 * Prints all employees currently in memory to stdout
	 */
	public void printAllEmployees() {
		for (Employee employee : this.employees) {
			System.out.println(employee.toString());
		}
	}
	
	/**
	 * Drives the program using a while loop that responds to user input from stdin.
	 * @param args
	 */
	public static void main(String[] args) {
		Company company = new Company();
		
		int input = 10;
		
		while(input != 0) {
			Scanner scnr = new Scanner(System.in);
			System.out.println("Commands:");
			System.out.println("1 for reading text file, 2 reading object file, "
					+ "3 for writing text file, 4 for writing object file, "
					+ "5 search by ID, 6 search by name, 7 search by department, "
					+ "8 add an employee, 9 remove an employee 10 print all employees, "
					+ "11 exit.");
			input = Integer.parseInt(scnr.nextLine());
			
			switch (input) {
			case 1:
				try {
					company.readEmployeeTextFile("employees.txt");
				} catch (IOException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					company.readEmployeeObjectFile("employees-out.ser");
				} catch (Exception e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					company.writeEmployeeTextFile("employees-out.txt");
				} catch (IOException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					company.writeEmployeeObjectFile("employees-out.ser");
				} catch (IOException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
			case 5:
				System.out.println("Enter ID Number:");
				input = Integer.parseInt(scnr.nextLine());
				System.out.println(company.searchID(input).toString());
				break;
			case 6:
				System.out.println("Enter name:");
				String name = scnr.nextLine();
				for (Object e : company.searchName(name)) {
					System.out.println( ((Employee) e).toString());
				}
				break;
			case 7:
				System.out.println("Enter department:");
				Department d = Department.parseDepartment(scnr.nextLine());
				for (Object e : company.searchDepartment(d)) {
					System.out.println(((Employee) e).toString());
				}
				break;
			case 8:
				System.out.println("Enter First Name, Last Name, "
						+ "ID, Department, and Salary:");
				String[] info = scnr.nextLine().split(",");
				company.addEmployee(new Employee(
						info[0], 
						info[1], 
						Integer.parseInt(info[2]),
						Department.parseDepartment(info[3]),
						Integer.parseInt(info[4])));
				break;
			case 9:
				System.out.println("Enter ID:");
				input = Integer.parseInt(scnr.nextLine());
				company.removeEmployee(input);
				break;
			case 10:
				company.printAllEmployees();
				break;
			case 11:
				return;
			default:
				System.err.println("Invalid Argument. Please try again.");
			}	
		}	
	}
}

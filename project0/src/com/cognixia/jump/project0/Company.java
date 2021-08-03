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
import java.util.List;
import java.util.Scanner;

import com.cognixia.jump.project0.Employee;
import com.cognixia.jump.project0.DuplicateEntryException;

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
	
	/**
	 * @author Noah Fryer
	 * 
	 * Enumeration of all the departments in the company. Enables
	 * easy search and comparison.
	 */
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
	private final ArrayList<Employee> employees;

	/**
	 * Default Constructor
	 */
	public Company() {
		this.employees = new ArrayList<Employee>();
	}
	
	/**
	 * Searches for an Employee object with a matching ID.
	 * Assumes that the internal employee collection is sorted.
	 * @param id
	 * @return an Employee object, or null if there are no matches
	 */
	public Employee searchID(int id) {
		int index = Collections.binarySearch(this.employees, new Employee(id));
		
		if (index >= 0) {
			return this.employees.get(index);
		} else {
			return null;
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
		e.getFirstName().equalsIgnoreCase(name) 
		|| e.getLastName().equalsIgnoreCase(name)
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
	public ArrayList<Employee> getEmployeeSet() {
		return this.employees;
	}
	
	/**
	 * Adds a single employee entry. Assumes that the 
	 * underlying collection is sorted.
	 * @param e
	 */
	public void insertEmployee(Employee e) throws DuplicateEntryException {
		int index = Collections.binarySearch(this.employees, e);
		
		if (index < 0) {
			this.employees.add( (-(index) - 1), e);
		} else {
			throw new DuplicateEntryException(e.toString() + " was not added. "
					+ "Duplicate ID entries are not allowed.");
		}
	}
	
	/**
	 * Adds new employee entries from the given set.
	 * 
	 * @param s
	 */
	public void insertEmployees(List<Employee> list) {
		for (Employee e : list) {
			try {
			insertEmployee(e);
			} catch (DuplicateEntryException exception) {
				System.err.println(exception.getMessage());
			}
		}
	}
	
	/**
	 * Removes an employee entry from memory.
	 * Assumes that the internal employee collection is sorted.
	 * @param id
	 */
	public void removeEmployee(int id) {
		int index = Collections.binarySearch(this.employees, new Employee(id));
		
		if (index >= 0) {
			this.employees.remove(index);
		} else {
			System.err.println(id + " does not match any records.");
		}
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
		this.insertEmployees(temp.getEmployeeSet());
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
			
			try {
			this.insertEmployee(
					new Employee(
							tokens[0],
							tokens[1],
							Integer.parseInt(tokens[2]),
							Department.parseDepartment(tokens[3]),
							Integer.parseInt(tokens[4])
					));
			} catch (DuplicateEntryException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Writes all employees to a text file. Fields are separated by commas,
	 * one employee per line
	 * 
	 * @param path
	 * @throws IOException
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
		
		int input = 99;
		
		while(true) {
			Scanner scnr = new Scanner(System.in);
			System.out.println("Commands:");
			System.out.println("1 for reading text file, 2 reading object file,\n"
					+ "3 for writing text file, 4 for writing object file,\n"
					+ "5 search by ID, 6 search by name, 7 search by department,\n"
					+ "8 add an employee, 9 remove an employee 10 print all employees,\n"
					+ "11 exit.");
			input = Integer.parseInt(scnr.nextLine());
			
			switch (input) {
			case 1:
				try {
					company.readEmployeeTextFile("employees.txt");
					System.out.println("Read employees.txt");
				} catch (IOException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					company.readEmployeeObjectFile("employees-out.ser");
					System.out.println("Read employees-out.ser");
				} catch (Exception e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					company.writeEmployeeTextFile("employees-out.txt");
					System.out.println("Wrote employees-out.txt");
				} catch (IOException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					company.writeEmployeeObjectFile("employees-out.ser");
					System.out.println("Read employees-out.ser");
				} catch (IOException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
				break;
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
				try {
				company.insertEmployee(new Employee(
						info[0], 
						info[1], 
						Integer.parseInt(info[2]),
						Department.parseDepartment(info[3]),
						Integer.parseInt(info[4])));
				} catch (DuplicateEntryException e) {
					System.err.println(e.getMessage());
				}
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
				scnr.close();
				return;
			default:
				System.err.println("Invalid Argument. Please try again.");
			}	
		}	
	}
}

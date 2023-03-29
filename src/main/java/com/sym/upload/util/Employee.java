/**
 * 
 */
package com.sym.upload.util;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author munir
 *
 */
//@Cacheable
@Entity
@Table(name = "employees")

public class Employee {
	@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
	private int emp_id;
	
	@Column(name = "emp_name")
	private String emp_name;
	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "emp_salary")
	private double emp_salary;
	
	@Column(name = "emp_address")
	private String emp_address;
	
	/**
	 * @return the emp_id
	 */
	public int getEmp_id() {
		return emp_id;
	}
	/**
	 * @param emp_id the emp_id to set
	 */
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	/**
	 * @return the emp_name
	 */
	public String getEmp_name() {
		return emp_name;
	}
	/**
	 * @param emp_name the emp_name to set
	 */
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}
	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	/**
	 * @return the emp_salary
	 */
	public double getEmp_salary() {
		return emp_salary;
	}
	/**
	 * @param emp_salary the emp_salary to set
	 */
	public void setEmp_salary(double emp_salary) {
		this.emp_salary = emp_salary;
	}
	/**
	 * @return the emp_address
	 */
	public String getEmp_address() {
		return emp_address;
	}
	/**
	 * @param emp_address the emp_address to set
	 */
	public void setEmp_address(String emp_address) {
		this.emp_address = emp_address;
	}
	@Override
	public String toString() {
		return "Employee [emp_id=" + emp_id + ", emp_name=" + emp_name + ", designation=" + designation
				+ ", emp_salary=" + emp_salary + ", emp_address=" + emp_address + "]";
	}
	
	
	

}

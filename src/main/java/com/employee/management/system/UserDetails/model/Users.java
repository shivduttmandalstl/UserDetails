package com.employee.management.system.UserDetails.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class Users {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", columnDefinition = "INT(10) UNIQUE KEY auto_increment" , updatable = false)
	private int id;
	
	@Id
	private String email;
	
	@Column(name = "password",nullable=false)
	private String password;
	
	@Column(name = "role",nullable=false)
	private String role;
	
	@Column(name = "firstName", nullable=false)
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;	
	
	@Column(name = "contact")
	private long contact;
	
	@Column(name = "jobLocation")
	private String jobLocation;

	@Column(name = "managerName")
	private String managerName;	

	@Column(name = "managerEmail")
	private String managerEmail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id ;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}


	
}

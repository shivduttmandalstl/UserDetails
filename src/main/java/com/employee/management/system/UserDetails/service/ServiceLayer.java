package com.employee.management.system.UserDetails.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.employee.management.system.UserDetails.model.Users;
import com.employee.management.system.UserDetails.repository.UserRepository;


@Service
public class ServiceLayer {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public List<Users> getAllEmployees() {
		return repository.findAll();	
	}
	
	public List<Users> getUsersByEmployees(String role) {
		return repository.findByRole(role);

	}
	
	public Optional<Users> getEmployeesById(String email) {
		return repository.findById(email);
	}
	

	public ResponseEntity<Users> addEmployee(Users user ){
		if(repository.existsById(user.getEmail())) {
			return new ResponseEntity<Users>(HttpStatus.ALREADY_REPORTED);
		}
		else {
		user.setPassword(passwordEncoder.encode(user.getPassword()));	
		return new ResponseEntity<Users>(repository.save(user),HttpStatus.CREATED);
		}
	}
	
	public ResponseEntity<Users> deleteEmployeeById(String email){
		if(repository.existsById(email)) {
			repository.deleteById(email);
			return new ResponseEntity<Users>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Users> updateEmployee(Users user ){
		if(repository.existsById(user.getEmail())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));	
			return new ResponseEntity<Users>(repository.save(user),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
		}	
	}
	
	public List<Users> getEmployeesByManagerId(String managerEmail) {
		return repository.findByManagerEmail(managerEmail);
	}
	
	public Boolean RoleVerification(String email, String role) {
		Users user = repository.findByEmailAndRole(email,role);
		if(user!=null) {
			return true;
		}
		else {
			return false;
		}
	}


}

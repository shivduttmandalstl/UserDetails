package com.employee.management.system.UserDetails.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.employee.management.system.UserDetails.jwtAuthentication.AuthRequest;
import com.employee.management.system.UserDetails.jwtAuthentication.JwtResponse;
import com.employee.management.system.UserDetails.model.Users;
import com.employee.management.system.UserDetails.service.JwtService;
import com.employee.management.system.UserDetails.service.ServiceLayer;


@RestController
@RequestMapping(path="/home")
@CrossOrigin(origins = "*")
public class HomeController {
	@Autowired
	ServiceLayer service;
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
		
	@GetMapping(path = "/all")
	public List<Users> getAllEmployees(){
	return service.getAllEmployees();
	}
	
	
	@GetMapping(path = "/all/role/{role}")
	public List<Users> getUsersByRole(@PathVariable String role){
	return service.getUsersByEmployees(role);
	}
	
	@GetMapping(path = "/all/{email}")
	public Optional<Users> getEmployeesById(@PathVariable String email) {
		return service.getEmployeesById(email);
	}
	
	
	@PostMapping(path = "/add")
	public @ResponseBody ResponseEntity<Users> addEmployee(@RequestBody Users user ){
		return service.addEmployee(user);
	}
	
	@DeleteMapping(path = "/delete/{email}")
	public ResponseEntity<Users> DeleteEmployeeById(@PathVariable String email){
		return service.deleteEmployeeById(email);
	}
	
	@PutMapping(path = "/update")
	public ResponseEntity<Users> updateEmployee(@RequestBody Users user ){
		return service.updateEmployee(user);
	}
	
	@GetMapping(path = "/manager/{managerEmail}")
	public List<Users> getEmployeesByManagerId(@PathVariable String managerEmail) {
		return service.getEmployeesByManagerId(managerEmail);
	}
	
	@PostMapping(path = "/authenticate")
    public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		if(service.RoleVerification(authRequest.getEmail(), authRequest.getRole())) {
			 Authentication authentication = authenticationManager.authenticate
		    		  (new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
				if (authentication.isAuthenticated()) {
					JwtResponse jwtResponse = new JwtResponse();
					jwtResponse.setToken(jwtService.generateToken(authRequest.getEmail()));
		            return jwtResponse;
		        } else {
		            throw new UsernameNotFoundException("invalid user request !");
		        }
			}
		else {
			throw new UsernameNotFoundException("invalid user request !");
		}
     


	}
	
}

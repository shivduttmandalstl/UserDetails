package com.employee.management.system.UserDetails.jwtAuthentication;

public class AuthRequest {

	private String email ;
    private String password;
    private String role;
	
    
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String username) {
		this.email = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}

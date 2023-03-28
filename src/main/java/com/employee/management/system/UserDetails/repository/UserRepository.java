package com.employee.management.system.UserDetails.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.management.system.UserDetails.model.Users;

public interface UserRepository extends JpaRepository<Users, String>{

	Optional<Users> findByEmail(String username);
	Users findByEmailAndRole(String email,String role);
	List<Users> findByManagerEmail(String managerEmail);
	List<Users> findByRole(String role);
	

}

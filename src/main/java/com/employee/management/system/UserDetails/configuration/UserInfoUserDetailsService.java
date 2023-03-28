package com.employee.management.system.UserDetails.configuration;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.employee.management.system.UserDetails.exception.UserNameNotFoundException;
import com.employee.management.system.UserDetails.model.Users;
import com.employee.management.system.UserDetails.repository.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService{
	@Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userInfo = repository.findByEmail(username);
        try {
			return userInfo.map(UserInfoUserDetails::new)
					.orElseThrow(()-> new UserNameNotFoundException("User Not Found"));
		} catch (UserNameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
         

    }

}

package com.employee.management.system.UserDetails.configuration;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.employee.management.system.UserDetails.model.Users;


@SuppressWarnings("serial")
public class UserInfoUserDetails implements UserDetails{
	
		private String email;
	   	private String password;


	    public UserInfoUserDetails(Users userInfo) {
	        email=userInfo.getEmail();
	        password=userInfo.getPassword();
	    }

	   

	    @Override
	    public String getPassword() {
	        return password;
	    }

	    @Override
	    public String getUsername() {
	        return email;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }



		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return null;
		}


}

package com.employee.management.system.UserDetails.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNameNotFoundException extends Exception{
	public UserNameNotFoundException(String msg) {
		super(msg);
	}

}

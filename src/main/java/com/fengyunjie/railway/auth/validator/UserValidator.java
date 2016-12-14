package com.fengyunjie.railway.auth.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fengyunjie.railway.auth.model.User;
import com.fengyunjie.railway.auth.service.UserService;

@Component
public class UserValidator implements Validator {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> arg0) {
		return User.class.equals(arg0);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User)o;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		
		if(user.getUsername().length() < 6 || user.getUsername().length() > 32){
			errors.rejectValue("username", "Size.userForm.username");
		}
		if(userService.findByUsername(user.getUsername()) != null){
			errors.rejectValue("username", "Duplcate.userForm.username");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		
		if(user.getPassword().length() < 8 || user.getPassword().length() > 32){
			errors.rejectValue("password", "Size.userForm.password");
		}
		
		if(!user.getPasswordConfirm().equals(user.getPassword())){
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
		}
	}

}

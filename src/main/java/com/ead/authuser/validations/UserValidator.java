package com.ead.authuser.validations;

import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class UserValidator
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 16/12/2024
 */
@Component
public class UserValidator implements Validator {

    private final Validator validator;
    final UserService userService;

    public UserValidator(@Qualifier("defaultValidator") Validator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRecordDto userRecordDto = (UserRecordDto) o;
        validator.validate(userRecordDto, errors);
        if(!errors.hasErrors()){
            validateUsername(userRecordDto, errors);
            validateEmail(userRecordDto, errors);
        }
    }

    private void validateUsername(UserRecordDto userRecordDto, Errors errors){
        if(userService.existsByUsername(userRecordDto.username())) {
            errors.rejectValue("username", "usernameConflict", "Error: Username is Already Taken!");
        }
    }

    private void validateEmail(UserRecordDto userRecordDto, Errors errors){
        if(userService.existsByEmail(userRecordDto.email())) {
            errors.rejectValue("email", "emailConflict", "Error: Email is Already Taken!");
        }
    }
}

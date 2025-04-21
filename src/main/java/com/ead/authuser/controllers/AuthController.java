package com.ead.authuser.controllers;

import com.ead.authuser.configs.security.JwtProvider;
import com.ead.authuser.dtos.JwtRecordDto;
import com.ead.authuser.dtos.LoginRecordDto;
import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.service.UserService;
import com.ead.authuser.validations.UserValidator;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class AuthController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 19/11/2024
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    final UserService userService;
    final UserValidator userValidator;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, UserValidator userValidator, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserRecordDto.UserView.RegistrationPost.class)
                                               @JsonView(UserRecordDto.UserView.RegistrationPost.class) UserRecordDto userRecordDto,
                                               Errors errors) {
        userValidator.validate(userRecordDto, errors);
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRecordDto));
    }

//    @PostMapping("/signup/admin/usr")
//    public ResponseEntity<Object> registerUserAdmin(@RequestBody @Validated(UserRecordDto.UserView.RegistrationPost.class)
//                                                    @JsonView(UserRecordDto.UserView.RegistrationPost.class) UserRecordDto userRecordDto,
//                                                    Errors errors){
//        userValidator.validate(userRecordDto, errors);
//        if(errors.hasErrors()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
//        }
//        return  ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUserAdmin(userRecordDto));
//    }

    @PostMapping("/login")
    public ResponseEntity<JwtRecordDto> authenticateUser(@RequestBody @Valid LoginRecordDto loginRecordDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRecordDto.username(), loginRecordDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JwtRecordDto(jwtProvider.generateJwt(authentication)));
    }
}

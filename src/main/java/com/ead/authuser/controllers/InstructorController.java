package com.ead.authuser.controllers;

import com.ead.authuser.dtos.InstructorRecordDto;
import com.ead.authuser.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class InstructorController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 16/12/2024
 */

@RestController
@RequestMapping("/instructors")
public class InstructorController {
    final UserService userService;

    public InstructorController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorRecordDto instructorRecordDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.registerInstructor(userService.findById(instructorRecordDto.userId()).get()));
    }
}

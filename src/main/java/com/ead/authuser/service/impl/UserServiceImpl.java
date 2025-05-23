package com.ead.authuser.service.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.enums.RoleType;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.exceptions.NotFoundException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.service.RoleService;
import com.ead.authuser.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The Class UserServiceImpl
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 19/11/2024
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final CourseClient courseClient;

    private final UserEventPublisher userEventPublisher;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, CourseClient courseClient, UserEventPublisher userEventPublisher, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.courseClient = courseClient;
        this.userEventPublisher = userEventPublisher;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);
        if (userModelOptional.isEmpty()) {
                throw new NotFoundException("User not found");
        }
        return userModelOptional;
    }

    @Override
    @Transactional
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(ActionType.DELETE));
    }

    @Transactional
    @Override
    public UserModel registerUser(UserRecordDto userRecordDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.USER);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModel.getRoles().add(roleService.findByRoleName(RoleType.ROLE_USER));
        userRepository.save(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(ActionType.CREATE));
        return userModel;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public UserModel updateUser(UserRecordDto userRecordDto, UserModel userModel) {
        userModel.setFullName(userRecordDto.fullName());
        userModel.setPhoneNumber(userRecordDto.phoneNumber());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userRepository.save(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(ActionType.UPDATE));
        return userModel;
    }

    @Override
    public void updatePassword(UserRecordDto userRecordDto, UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userRecordDto.password()));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userRepository.save(userModel);
    }

    @Transactional
    @Override
    public UserModel updateImage(UserRecordDto userRecordDto, UserModel userModel) {
        userModel.setImageUrl(userRecordDto.imageUrl());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userRepository.save(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(ActionType.UPDATE));
        return userModel;
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public UserModel registerInstructor(UserModel userModel) {
        userModel.setUserType(UserType.INSTRUCTOR);
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.getRoles().add(roleService.findByRoleName(RoleType.ROLE_INSTRUCTOR));
        userRepository.save(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(ActionType.UPDATE));
        return userModel;
    }

    @Transactional
    @Override
    public UserModel registerUserAdmin(UserRecordDto userRecordDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.ADMIN);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModel.getRoles().add(roleService.findByRoleName(RoleType.ROLE_ADMIN));
        userRepository.save(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(ActionType.CREATE));
        return userModel;
    }
}

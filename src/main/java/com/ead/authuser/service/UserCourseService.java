package com.ead.authuser.service;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * The Interface UserCourseService
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 12/12/2024
 */
public interface UserCourseService {
    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

    UserCourseModel save(UserCourseModel userCourseModel);

    boolean existsByCourseId(UUID courseId);

    void deleteAllByCourseId(UUID courseId);
}

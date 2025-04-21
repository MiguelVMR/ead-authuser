package com.ead.authuser.configs.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * The Class AuthenticationCurrentUserService
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 21/04/2025
 */
@Service
public class AuthenticationCurrentUserService {
    public UserDetailsImpl getCurrentUser(){
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

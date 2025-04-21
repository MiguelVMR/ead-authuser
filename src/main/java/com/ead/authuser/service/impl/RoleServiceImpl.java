package com.ead.authuser.service.impl;

import com.ead.authuser.enums.RoleType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.repositories.RoleRepository;
import com.ead.authuser.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * The Class RoleServiceImpl
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 12/03/2025
 */
@Service
public class RoleServiceImpl implements RoleService {

    final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleModel findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType)
                .orElseThrow(() -> new RuntimeException("Error: Role is Not Found."));
    }
}

package com.ead.authuser.service;

import com.ead.authuser.enums.RoleType;
import com.ead.authuser.models.RoleModel;

/**
 * The Interface RoleService
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 12/03/2025
 */
public interface RoleService {

    RoleModel findByRoleName(RoleType roleType);
}

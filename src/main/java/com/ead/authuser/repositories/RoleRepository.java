package com.ead.authuser.repositories;

import com.ead.authuser.enums.RoleType;
import com.ead.authuser.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * The Interface RoleRepository
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 12/03/2025
 */
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByRoleName(RoleType name);
}

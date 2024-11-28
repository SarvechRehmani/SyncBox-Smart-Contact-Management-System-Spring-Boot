package com.syncbox.services;

import com.syncbox.models.entities.Role;

import java.util.Optional;

public interface RoleService {

    public Optional<Role> getRoleById(Long id);

    public Role saveRole(Role role);
}

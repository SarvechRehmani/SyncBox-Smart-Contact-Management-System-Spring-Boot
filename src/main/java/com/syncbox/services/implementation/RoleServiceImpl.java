package com.syncbox.services.implementation;

import com.syncbox.models.entities.Role;
import com.syncbox.repositories.RoleRepository;
import com.syncbox.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return this.roleRepository.findById(id);
    }

    @Override
    public Role saveRole(Role role) {
        return this.roleRepository.save(role);
    }
}

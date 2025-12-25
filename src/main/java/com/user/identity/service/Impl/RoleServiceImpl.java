package com.user.identity.service.Impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.user.identity.repository.entity.Permission;
import com.user.identity.repository.entity.Role;
import org.springframework.stereotype.Service;

import com.user.identity.controller.dto.request.RoleRequest;
import com.user.identity.controller.dto.response.RoleResponse;
import com.user.identity.mapper.RoleMapper;
import com.user.identity.repository.PermissionRepository;
import com.user.identity.repository.RoleRepository;
import com.user.identity.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toRole(request);

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissions()));
        role.setPermissions(permissions);

        Role savedRole = roleRepository.save(role);

        return roleMapper.toRoleResponse(savedRole);
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public String delete(String roleId) {
        roleRepository.deleteById(roleId);
        return "Delete Successfully";
    }
}

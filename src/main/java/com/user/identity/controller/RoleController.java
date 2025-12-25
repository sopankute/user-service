package com.user.identity.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.user.identity.controller.dto.ApiResponse;
import com.user.identity.controller.dto.request.RoleRequest;
import com.user.identity.controller.dto.response.RoleResponse;
import com.user.identity.facade.RoleFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Tag(
        name = "Role Controller",
        description = "API for managing user roles"
)
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@SecurityRequirement(name = "bearerAuth") // Apply JWT authentication requirement to all endpoints in this controller

public class RoleController {

    RoleFacade roleFacade;

    /**
     * Create a new role.
     *
     * @param request role information to be created
     * @return a successful response containing the newly created role information
     */
    @Operation(
            summary = "Create a new role",
            description = "This API allows creating a new role in the system."
            ,security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires a Bearer token with an ADMIN role
    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        var result = roleFacade.create(request);
        return ApiResponse.success(result);
    }

    /**
     * Retrieve all roles.
     *
     * @return a list of all roles available in the system
     */
    @Operation(
            summary = "Get all roles",
            description = "This API returns a list of all roles available in the system.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")      // Requires Bearer token with ADMIN privileges
    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        var result = roleFacade.getAll();
        return ApiResponse.success(result);
    }

    /**
     * Delete a role by name.
     *
     * @param role the name of the role to be deleted
     * @return a response indicating the result of the role deletion
     */
    @Operation(
            summary = "Delete role",
            description = "This API allows deleting a role based on the provided role name.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires a Bearer token with an ADMIN role
    @DeleteMapping("/{role}")
    public ApiResponse<String> delete(@PathVariable String role) {
        var result = roleFacade.delete(role);
        return ApiResponse.success(result);
    }
}

package com.user.identity.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.user.identity.controller.dto.ApiResponse;
import com.user.identity.controller.dto.request.PermissionRequest;
import com.user.identity.controller.dto.response.PermissionResponse;
import com.user.identity.facade.PermissionFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Tag(
        name = "Permission Controller",
        description = "API for managing user permissions"
)
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@SecurityRequirement(name = "bearerAuth") // Apply JWT authentication requirement to all endpoints in this controller
public class PermissionController {

    PermissionFacade permissionFacade;

    /**
     * Create a new permission.
     *
     * @param request information of the permission to create
     * @return success response with the newly created permission details
     */
    @Operation(
            summary = "Create new permission",
            description = "This API allows creating a new permission in the system.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires a Bearer token with an ADMIN role
    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        var result = permissionFacade.create(request);
        return ApiResponse.success(result);
    }

    /**
     * Get a list of all permissions.
     *
     * @return list of all available permissions in the system
     */
    @Operation(
            summary = "Get all permissions",
            description = "This API returns a list of all available permissions in the system.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires a Bearer token with an ADMIN role
    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        var result = permissionFacade.getAll();
        return ApiResponse.success(result);
    }

    /**
     * Delete a permission by ID.
     *
     * @param permission ID of the permission to delete
     * @return response message indicating the result of the deletion
     */
    @Operation(
            summary = "Delete permission",
            description = "This API allows deleting a permission based on its ID.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires a Bearer token with an ADMIN role
    @DeleteMapping("/{permission}")
    public ApiResponse<String> delete(@PathVariable String permission) {
        var result = permissionFacade.delete(permission);
        return ApiResponse.success(result);
    }
}

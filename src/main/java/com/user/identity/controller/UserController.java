package com.user.identity.controller;

import com.user.identity.controller.dto.ApiResponse;
import com.user.identity.controller.dto.request.UserCreationRequest;
import com.user.identity.controller.dto.request.UserUpdateRequest;
import com.user.identity.controller.dto.response.InfoUserForCount;
import com.user.identity.controller.dto.response.UserResponse;
import com.user.identity.facade.UserFacade;
import com.user.identity.facade.VerifyFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Tag(
        name = "User Controller",
        description = "Manage user-related operations such as creation, updating, deletion, verification, etc."
)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserFacade userFacade;
    VerifyFacade verifyFacade;

    /**
     * Create a new user.
     *
     * @param request user information to be created
     * @return success response with the newly created user information
     */
    @Operation(
            summary = "Create a new user",
            description = "Register a new user with the provided information.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping("/create")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        var result = userFacade.createUser(request);
        return ApiResponse.success(result);
    }

    /**
     * Retrieve all users.
     *
     * @return list of all registered users
     */
    @Operation(
            summary = "Get all users",
            description = "Retrieve a list of all registered users in the system.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with an ADMIN role
    public ApiResponse<List<UserResponse>> getUsers() {
        var result = userFacade.getUsers();
        return ApiResponse.success(result);
    }

    /**
     * Get user information by ID.
     *
     * @param userId ID of the user
     * @return user information by ID
     */
    @Operation(
            summary = "Get user by ID",
            description = "Retrieve information of a specific user by their ID.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/get-by-id/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable("userId") int userId) {
        var result = userFacade.getUser(userId);
        return ApiResponse.success(result);
    }

    /**
     * Get current authenticated user's information.
     *
     * @return information of the currently authenticated user
     */
    @Operation(
            summary = "Get current user info",
            description = "Retrieve information of the currently logged-in user.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        var result = userFacade.getMyInfo();
        return ApiResponse.success(result);
    }

    /**
     * Delete a user by ID.
     *
     * @param userId ID of the user to delete
     * @return success response after deleting the user
     */
    @Operation(
            summary = "Delete user by ID",
            description = "Delete a user with the provided ID.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with an ADMIN role
    public ApiResponse<String> deleteUser(@PathVariable int userId) {
        var result = userFacade.deleteUser(userId);
        return ApiResponse.success(result);
    }

    /**
     * Update user information.
     *
     * @param request user information to be updated
     * @return success response with updated user information
     */
    @Operation(
            summary = "Update user information",
            description = "Update user information with the provided details.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PutMapping("/update")
    public ApiResponse<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        var result = userFacade.updateUser(request);
        return ApiResponse.success(result);
    }

    /**
     * Get authenticated user details.
     *
     * @return information of the currently authenticated user
     */
    @Operation(
            summary = "Get authenticated user details",
            description = "Retrieve information of the currently authenticated and logged-in user.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/me")
    public ApiResponse<UserResponse> getMe() {
        var result = userFacade.getMe();
        return ApiResponse.success(result);
    }

    /**
     * Verify user's email.
     *
     * @param token verification token
     * @return confirmation response for successful email verification
     */
    @Operation(
            summary = "Verify user email",
            description = "Verify the user's email using the provided token.")
    @GetMapping("/verify-email")
    public ApiResponse<Map<String, Object>> verifyEmail(@RequestParam("token") String token) {
        var result = verifyFacade.verifyEmail(token);
        return ApiResponse.success(result);
    }

    /**
     * Resend an email verification link.
     *
     * @param email email address to resend a verification link
     * @return confirmation response for a resending verification link
     */
    @Operation(
            summary = "Resend email verification link",
            description = "Resend the email verification link to the specified email address."
    )
    @PostMapping("/resend-verification")
    public ApiResponse<String> resendVerification(@RequestParam("email") String email) {
        var result = verifyFacade.resendVerification(email);
        return ApiResponse.success(result);
    }

    /**
     * Upload user avatar.
     *
     * @param userId ID of the user
     * @param file avatar image file to upload
     * @return uploaded avatar information
     */
    @Operation(
            summary = "Upload avatar",
            description = "Upload avatar.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping(value = "/upload-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> uploadPostImages(@RequestParam int userId, @RequestPart(value = "file", required = false) MultipartFile file) {
        String uploadedImages = userFacade.uploadAvatar(userId, file);
        return ApiResponse.success(uploadedImages);
    }

    @GetMapping("/quantityUser")
    public ApiResponse<InfoUserForCount> countUser ()
    {
        return ApiResponse.success(userFacade.countUser());
    }
}

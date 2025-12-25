package com.user.identity.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.identity.controller.dto.ApiResponse;
import com.user.identity.controller.dto.request.AuthenticationRequest;
import com.user.identity.controller.dto.request.IntrospectRequest;
import com.user.identity.controller.dto.request.LogoutRequest;
import com.user.identity.controller.dto.request.RefreshRequest;
import com.user.identity.controller.dto.response.AuthenticationResponse;
import com.user.identity.controller.dto.response.IntrospectResponse;
import com.user.identity.facade.AuthenticationFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Tag(
        name = "Authentication Controller",
        description = "Endpoints for user authentication and token management")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    AuthenticationFacade authenticationFacade;

    /**
     * Authenticate a user and generate a token.
     *
     * @param authRequest authentication request containing user credentials
     * @return success response containing the authentication token
     */

    @Operation(
            summary = "User login",
            description = "Authenticate the user and return a JWT token."
    )
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest) {
        System.out.println("AuthenticationController.authenticate"
                +" | AuthenticationRequest :: "+authRequest);
        AuthenticationResponse result = authenticationFacade.authenticate(authRequest);
        System.out.println("AuthenticationController.authenticate"
                +"| AuthenticationResponse :: "+result);
        return ApiResponse.success(result);
    }

    /**
     * Check the validity of a token.
     *
     * @param request introspect request containing the token
     * @return response indicating whether the token is valid
     */

    @Operation(
            summary = "Check token",
            description = "Check if the token is valid."
    )
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws Exception {
        var result = authenticationFacade.introspect(request);
        return ApiResponse.success(result);
    }

    /**
     * Refresh an authentication token.
     *
     * @param request refresh request containing the old token
     * @return success response containing a new token
     */

    @Operation(
            summary = "Refresh token",
            description = "Generate a new token using the refresh token."
    )
    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request) throws Exception {
        var result = authenticationFacade.refreshToken(request);
        return ApiResponse.success(result);
    }

    /**
     * Logout a user and invalidate the token.
     *
     * @param request logout request containing the token to be invalidated
     * @return response indicating the result of the logout operation
     */

    @Operation(
            summary = "User logout",
            description = "Invalidate the user's token to log out."
    )
    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody LogoutRequest request) throws Exception {
        var result = authenticationFacade.logout(request);
        return ApiResponse.success(result);
    }
}


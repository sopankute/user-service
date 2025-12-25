package com.user.identity.controller;

import com.user.identity.controller.dto.ApiResponse;
import com.user.identity.controller.dto.request.UpgradeSubscriptionRequest;
import com.user.identity.controller.dto.response.SubscriptionResponse;
import com.user.identity.service.Impl.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @Operation(
            summary = "Check User's post permissions",
            description = "This API Validate whether the user can publish a post.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/check-post-permission")
    public ApiResponse<Boolean> canCreatePost(@RequestHeader("userId") Integer userId)
    {
        System.out.println("SubscriptionController.canCreatePost"
            +"| userId :: "+userId);
        return ApiResponse.success(subscriptionService.canCreatePost(userId));
    }


    @Operation(
            summary = "Check User's post permissions",
            description = "This API Validate whether the user can publish a post.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping("/decrement-free-post")
    public ApiResponse<String> decrementFreePost(@RequestHeader("userId") Integer userId)
    {
        subscriptionService.decrementFreePost(userId);
        return ApiResponse.success("Success");
    }


    @Operation(
            summary = "Check User's post permissions",
            description = "This API Validate whether the user can publish a post.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping("/upgrade")
    public ApiResponse<SubscriptionResponse> upgradeToPremium(
            @RequestBody UpgradeSubscriptionRequest request,
            @RequestHeader("userId") Integer userId)
    {
        subscriptionService.upgradeToPremium(userId, request.getPaymentAmount());
        return ApiResponse.success(subscriptionService.getSubscriptionInfo(userId));
    }


    @Operation(
            summary = "Check User's post permissions",
            description = "This API Validate whether the user can publish a post.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping("/renew")
    public ApiResponse<SubscriptionResponse> renewPremium(
            @RequestBody UpgradeSubscriptionRequest request,
            @RequestHeader("userId") Integer userId)
    {
        subscriptionService.renewPremium(userId, request.getPaymentAmount());
        return ApiResponse.success(subscriptionService.getSubscriptionInfo(userId));
    }
}

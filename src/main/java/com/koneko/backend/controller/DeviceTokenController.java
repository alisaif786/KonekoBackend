package com.koneko.backend.controller;

import com.koneko.backend.dto.DeviceTokenRequest;
import com.koneko.backend.service.DeviceTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device-token")
public class DeviceTokenController {

    private final DeviceTokenService deviceTokenService;

    public DeviceTokenController(
            DeviceTokenService deviceTokenService) {

        this.deviceTokenService = deviceTokenService;
    }

    @PostMapping
    public ResponseEntity<String> saveToken(
            @RequestBody DeviceTokenRequest request,
            Authentication authentication) {

        System.out.println("========== DEVICE TOKEN DEBUG ==========");

        System.out.println("Authentication object: " + authentication);

        if (authentication == null) {
            System.out.println("ERROR: Authentication is NULL");
            return ResponseEntity
                    .status(401)
                    .body("User not authenticated");
        }

        System.out.println("Authenticated user: "
                + authentication.getName());

        System.out.println("FCM Token: "
                + request.getToken());

        deviceTokenService.saveToken(
                authentication.getName(),
                request
        );

        System.out.println("Device token saved successfully");

        System.out.println("========================================");

        return ResponseEntity.ok(
                "Device token saved successfully."
        );
    }
}
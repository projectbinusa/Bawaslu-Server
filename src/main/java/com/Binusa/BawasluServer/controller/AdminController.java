package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.UserLoginRequest;
import com.Binusa.BawasluServer.DTO.UserRegistrationRequest;
import com.Binusa.BawasluServer.response.CustomResponse;
import com.Binusa.BawasluServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/admin/register")
    public ResponseEntity<String> registerAdmin(@RequestBody UserRegistrationRequest registrationRequest) {
        if (userService.registerAdmin(registrationRequest)) {
            return ResponseEntity.ok("Pendaftaran berhasil.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Gagal mendaftar.");
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<CustomResponse> loginAdmin(@RequestBody UserLoginRequest loginRequest) {
        CustomResponse response = userService.loginAdmin(loginRequest);
        return ResponseEntity.ok(response);
    }

}


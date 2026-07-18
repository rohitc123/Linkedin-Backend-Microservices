package com.example.linkedInApp.user_service.controller;

import com.example.linkedInApp.user_service.dto.LoginRequestDto;
import com.example.linkedInApp.user_service.dto.SignUpRequestDto;
import com.example.linkedInApp.user_service.dto.UserDto;
import com.example.linkedInApp.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        UserDto userDto=authService.signUp(signUpRequestDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        String token=authService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }


}

package dev.anubhav.userservice.controller;

import dev.anubhav.userservice.dtos.LoginRequestDto;
import dev.anubhav.userservice.dtos.SignUpRequestDto;
import dev.anubhav.userservice.dtos.UserDto;
import dev.anubhav.userservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        UserDto responseDto = authService.signUp(requestDto.getEmail(), requestDto.getPassword());
        return ResponseEntity.ok(responseDto);
    }

    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto requestDto) throws Exception {
        UserDto responseDto = authService.login(requestDto.getEmail(), requestDto.getPassword());
        return ResponseEntity.ok(responseDto);
    }
}

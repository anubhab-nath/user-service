package dev.anubhav.userservice.controller;

import dev.anubhav.userservice.dtos.LoginDto;
import dev.anubhav.userservice.dtos.SessionDto;
import dev.anubhav.userservice.dtos.UserDto;
import dev.anubhav.userservice.exceptions.NotFoundException;
import dev.anubhav.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    ResponseEntity<UserDto> registerUser(@RequestBody UserDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(requestDto));
    }

    @PostMapping("/login")
    ResponseEntity<SessionDto> login(@RequestBody LoginDto loginDto) throws NotFoundException {
        return ResponseEntity.ok(userService.login(loginDto));
    }
}

package dev.anubhav.userservice.dtos;

import lombok.Getter;

@Getter
public class LoginDto {
    private String email;
    private String password;
}

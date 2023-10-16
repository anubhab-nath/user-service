package dev.anubhav.userservice.services;

import dev.anubhav.userservice.dtos.UserDto;
import dev.anubhav.userservice.models.User;
import dev.anubhav.userservice.repos.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto signUp(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }
}

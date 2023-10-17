package dev.anubhav.userservice.services;

import dev.anubhav.userservice.dtos.UserDto;
import dev.anubhav.userservice.models.Session;
import dev.anubhav.userservice.repos.SessionRepository;
import dev.anubhav.userservice.models.SessionStatus;
import dev.anubhav.userservice.models.User;
import dev.anubhav.userservice.repos.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public AuthService(
            UserRepository userRepository,
            SessionRepository sessionRepository
    ) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public UserDto signUp(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public UserDto login(String email, String password) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty())
            throw new Exception("Invalid Email!!");

        User user = userOptional.get();
        if(!user.getPassword().equals(password))
            throw new Exception("Invalid Password!!");

        String token = RandomStringUtils.randomAlphanumeric(30);

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        UserDto userDto = new UserDto();
        return userDto;
    }
}

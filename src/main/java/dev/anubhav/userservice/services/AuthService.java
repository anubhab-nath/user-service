package dev.anubhav.userservice.services;

import dev.anubhav.userservice.dtos.UserDto;
import dev.anubhav.userservice.models.Session;
import dev.anubhav.userservice.repos.SessionRepository;
import dev.anubhav.userservice.models.SessionStatus;
import dev.anubhav.userservice.models.User;
import dev.anubhav.userservice.repos.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(
            UserRepository userRepository,
            SessionRepository sessionRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto signUp(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public UserDto login(String email, String password) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty())
            throw new Exception("Invalid Email!!");

        User user = userOptional.get();
        if(!passwordEncoder.matches(password, user.getPassword()))
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

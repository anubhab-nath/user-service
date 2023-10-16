package dev.anubhav.userservice.services;

import dev.anubhav.userservice.dtos.LoginDto;
import dev.anubhav.userservice.dtos.SessionDto;
import dev.anubhav.userservice.dtos.UserDto;
import dev.anubhav.userservice.exceptions.NotFoundException;
import dev.anubhav.userservice.models.User;
import dev.anubhav.userservice.repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto createUser(UserDto requestDto) {
        System.out.println(requestDto);
        // todo: check if the user is already present -> unique value from user
        Optional<User> storedUser = userRepository.findByEmailId(requestDto.getEmail());
        if(storedUser.isEmpty()) {
            User user = new User();
            user.setPassword(requestDto.getPassword());
            user.setEmail(requestDto.getEmail());

            User savedUser = userRepository.save(user);

            UserDto responseDto = new UserDto();
            responseDto.setEmail(savedUser.getEmail());
            responseDto.setPassword(savedUser.getPassword());

            return responseDto;
        }

        return modelMapper.map(storedUser, UserDto.class);
    }

    public SessionDto login(LoginDto loginDto) throws NotFoundException {
        // todo: check if login details are correct or not
        User storedUser = userRepository.findByEmailId(loginDto.getEmail())
                .orElseThrow(() -> new NotFoundException("User with email: " + loginDto.getEmail() + " is not present."));
        if(loginDto.getPassword().equals(storedUser.getPassword()))
            return new SessionDto();
        return null;
    }
}

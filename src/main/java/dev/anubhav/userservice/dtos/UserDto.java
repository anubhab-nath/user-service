package dev.anubhav.userservice.dtos;

import dev.anubhav.userservice.models.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    private String email;
    private String password;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(userDto.email);
        userDto.setPassword(userDto.password);
        return userDto;
    }
}

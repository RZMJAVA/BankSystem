package com.example.demo.mappers;

import com.example.demo.UserOperationsDto.UserRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setBalance(user.getBalance());
        userDto.setDepositBalance(user.getDepositBalance());
        return userDto;
    }

}

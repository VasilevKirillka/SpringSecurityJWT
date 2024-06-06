package com.example.SpringSecurityJWT.mapper;

import com.example.SpringSecurityJWT.dto.UserDto;
import com.example.SpringSecurityJWT.model.RoleType;
import com.example.SpringSecurityJWT.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    UserDto userToUserDto(User user);

    List<UserDto> usersToUserDtos(List<User> users);

}

package com.example.SpringSecurityJWT.service;

import com.example.SpringSecurityJWT.dto.RegistrationUserDto;
import com.example.SpringSecurityJWT.dto.UserDto;
import com.example.SpringSecurityJWT.exception.AppError;
import com.example.SpringSecurityJWT.mapper.UserMapper;
import com.example.SpringSecurityJWT.model.RoleType;
import com.example.SpringSecurityJWT.model.User;
import com.example.SpringSecurityJWT.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper mapper;


    @Test
    void testCreateUser() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto("user",
                "user@example.com", "password", "password");

        userService.createUser(registrationUserDto);

        verify(userRepository, times(1)).save(any(User.class));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();


        assertThat(savedUser.getUsername()).isEqualTo("user");
        assertThat(savedUser.getEmail()).isEqualTo("user@example.com");
        assertThat(savedUser.getPassword()).isNotEmpty();
        assertThat(savedUser.getRoles()).containsExactly(RoleType.ROLE_USER);

    }

    @Test
    void getAll() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1");
        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2");
        UserDto userDto1 = new UserDto();
        userDto1.setUsername("user1");
        userDto1.setEmail("user1");
        UserDto userDto2 = new UserDto();
        userDto2.setUsername("user2");
        userDto2.setEmail("user2");
        List<User> users = List.of(user1, user2);
        List<UserDto> expectedDtos = List.of(userDto1, userDto2);
        when(userRepository.findAll()).thenReturn(users);
        when(mapper.usersToUserDtos(users)).thenReturn(expectedDtos);

        List<UserDto> result = userService.getAll();

        assertEquals(2, result.size());
        assertEquals(expectedDtos.get(0).getUsername(), result.get(0).getUsername());
        assertEquals(expectedDtos.get(1).getUsername(), result.get(1).getUsername());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setRoles(List.of(RoleType.ROLE_USER));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<?> result = userService.delete(userId);

        Mockito.verify(userRepository, times(1)).deleteById(userId);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("Пользователь с id " + userId + " удален");
    }

    @Test
    void testDeleteAdmin() {
        Long userId = 1L;
        User adminUser = new User();
        adminUser.setId(userId);
        adminUser.setRoles(List.of(RoleType.ROLE_ADMIN));

        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));

        ResponseEntity<?> result = userService.delete(userId);
        AppError expectedError = new AppError(HttpStatus.BAD_REQUEST.value(),
                "Нельзя удалить пользователя с ролью админ");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getBody()).isEqualTo(expectedError);
    }
}
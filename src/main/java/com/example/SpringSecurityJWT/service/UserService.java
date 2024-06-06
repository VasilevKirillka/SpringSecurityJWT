package com.example.SpringSecurityJWT.service;

import com.example.SpringSecurityJWT.dto.RegistrationUserDto;
import com.example.SpringSecurityJWT.dto.UnsecuredDto;
import com.example.SpringSecurityJWT.dto.UserDto;
import com.example.SpringSecurityJWT.exception.AppError;
import com.example.SpringSecurityJWT.mapper.UserMapper;
import com.example.SpringSecurityJWT.model.RoleType;
import com.example.SpringSecurityJWT.model.User;
import com.example.SpringSecurityJWT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserMapper mapper;


    public void createUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setEmail(registrationUserDto.getEmail());
        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(RoleType.ROLE_USER));
        userRepository.save(user);
        log.info("Пользователь {} зарегистрирован", user.getUsername());
    }

    public List<UserDto> getAll() {
        var users = userRepository.findAll();
        return mapper.usersToUserDtos(users);
    }

    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public ResponseEntity<?> delete(Long id) {
        var user = userRepository.findById(id);
        if (user.isPresent()) {
            var admin = user.get();
            if (admin.getRoles().contains(RoleType.ROLE_ADMIN)) {
                log.info("Нельзя удалить пользователя с ролью админ");
                return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                        "Нельзя удалить пользователя с ролью админ"), HttpStatus.BAD_REQUEST);
            }
            userRepository.deleteById(id);
            log.info("Пользователь с id " + id + " удален");
            return ResponseEntity.ok().body("Пользователь с id " + id + " удален");
        } else {
            log.info("Пользователь с данным id не найден");
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Пользователь с данным id не найден"), HttpStatus.NOT_FOUND);
        }
    }

    public UnsecuredDto getTitle() {
        return new UnsecuredDto();
    }

    public UserDto getInfo(Principal principal) {
        String username = principal.getName();
        return mapper.userToUserDto(userRepository.findByUsername(username).get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));

        var authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                authorities);

    }
}

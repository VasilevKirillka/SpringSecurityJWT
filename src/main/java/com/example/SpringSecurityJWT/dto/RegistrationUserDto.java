package com.example.SpringSecurityJWT.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные для регистрации")
public class RegistrationUserDto {
    @Schema(description = "Имя пользователя/логин", example = "user")
    private String username;
    @Schema(description = "Почта", example = "user@user.com")
    private String email;
    @Schema(description = "Пароль", example = "1234")
    private String password;
    @Schema(description = "Проверка пароля", example = "1234")
    private String confirmPassword;
}

package com.example.SpringSecurityJWT.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные для аутентификации")
public class AuthRequest {
    @Schema(description = "Имя пользователя", example = "user")
    private String username;
    @Schema(description = "Пароль", example = "1234")
    private String password;
}

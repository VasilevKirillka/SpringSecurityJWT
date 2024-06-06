package com.example.SpringSecurityJWT.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Токен")
public class AuthResponse {
    @Schema(description = "Токен для аутентификации")
    private String token;
}

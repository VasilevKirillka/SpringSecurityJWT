package com.example.SpringSecurityJWT.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Описание ошибки")
public class AppError {
    @Schema(description = "Статус ошибки", example = "400")
    private int status;
    @Schema(description = "Сообщение ошибки", example = "Пользователь с данным id не найден")
    private String message;
    @Schema(description = "Время ошибки", example = "2024-06-05T13:13:41.574+00:00")
    private Date timestamp;

    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}

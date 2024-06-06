package com.example.SpringSecurityJWT.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Данные для всех")
public class UnsecuredDto {
    @Schema(description = "Любой заголовок/текст")
    private String title;
    @Schema(description = "Дата и Время")
    private LocalDateTime localDateTime;

    public UnsecuredDto() {
        this.title = "Эта страница доступна для всех пользователей";
        this.localDateTime = LocalDateTime.now();
    }
}

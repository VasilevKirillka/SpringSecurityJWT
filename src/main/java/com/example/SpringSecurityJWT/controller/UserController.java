package com.example.SpringSecurityJWT.controller;

import com.example.SpringSecurityJWT.dto.UnsecuredDto;
import com.example.SpringSecurityJWT.dto.UserDto;
import com.example.SpringSecurityJWT.exception.AppError;
import com.example.SpringSecurityJWT.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Управление данными пользователей", description = "Методы для вывода информации пользователей")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Открытая информация",
            description = "Метод для вывода информации доступной всем пользователят, в том чисте и неавторизоранным")
    @ApiResponse(responseCode = "200", description = "Инфо", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UnsecuredDto.class))
    })
    @GetMapping("/unsecured")
    public ResponseEntity<?> unsecuredData(){
        return ResponseEntity.ok(userService.getTitle());
    }

    @Operation(summary = "Данные пользователя",
            description = "Метод для вывода информации о пользователе, доступен только авторизованным пользователям")
            @ApiResponse(responseCode = "200",  content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))
            })
    @GetMapping("/info")
    public ResponseEntity<?> userData(Principal principal){
        return ResponseEntity.ok(userService.getInfo(principal));
    }

    @Operation(summary = "Данные для админа",
            description = "Метод для вывода информации о всех пользователях, доступен только для Админа")
    @ApiResponse(responseCode = "200", description = "Токен для аутентификации", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))
    })
    @GetMapping("/admin")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(userService.getAll());
    }

    @Operation(summary = "Удаление пользователя по id",
            description = "Метод для удаления пользователя, доступен только для Админа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удален",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "400", description = "Нельзя удалить пользователя с ролью админ",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь с данным id не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AppError.class)))
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

}

# SpringSecurityJWT:

Реализация приложения `SpringSecurityJWT` с использованием Spring Security и JWT для аутентификации и авторизации пользователей.

# Запуск и использование:

Чтобы запустить контейнер PostgreSQL вам нужно выполнить следующую команду в терминале: 
`docker run -d --name <имя вашего контейнера> -e POSTGRES_USER=<имя пользователя> -e POSTGRES_PASSWORD=<пароль> -e POSTGRES_DB=<название БД> -p 5432:5432 postgres`
Проверьте, чтобы эти данные совпадали с данными в настройках приложения:
```
  datasource:
    url: jdbc:postgresql://localhost:5432/<название БД>
    username: <имя пользователя>
    password: <пароль>
```


Командой `git clone <URL__репозитория>` клонировать проект и запустить в IntellIj Idea. По умолчанию проект будет находится на порту `8080`.

Для доступа к документации перейти на `http://localhost:8080/sw-ui`

# Стек технологий:
 `Java 17` `Spring Boot` `Spring Security` `Spring Web MVC` `Spring Data JPA` `Springdoc OpenAPI` `Java JWT` `PostgreSQL`  `Lombok` `MapStruct`

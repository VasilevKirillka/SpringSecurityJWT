INSERT INTO users (username, email, password)
SELECT 'admin', 'admin@example.com', '$2a$07$dv7NfdYrsdiR/T6Cylz22OCncoNw05eDtVz7pJXXe2Ov1d9omTOEK'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO user_roles (user_id, roles)
SELECT id, 'ROLE_ADMIN'
FROM users
WHERE username = 'admin'
  AND NOT EXISTS (SELECT 1 FROM user_roles WHERE user_id = (SELECT id FROM users WHERE username = 'admin'));

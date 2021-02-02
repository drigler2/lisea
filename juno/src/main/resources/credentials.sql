INSERT INTO credentials.authority(id, authority_name)
    VALUES
        (-2, 'ROLE_TEST'),
        (-1, 'TEST'),
        (0, 'ADMIN'),
        (1, 'USER_REGISTERED')
ON CONFLICT DO NOTHING;

INSERT INTO credentials.user(id, username, password, unique_id, enabled)
    VALUES
        (0, 'admin', '$2y$12$xqOODr.3z/W3VMJK8zqWouW4PWDW11xVeg9l19L9hh0pBcC9HYOuG', 66, true)
ON CONFLICT DO NOTHING;

INSERT INTO credentials.user_authority(id_user, id_authority, enabled)
    VALUES
        (0, 0, true)
ON CONFLICT DO NOTHING;

-- SCHEMA: credentials

-- DROP SCHEMA credentials ;

CREATE SCHEMA credentials
    AUTHORIZATION juno_admin;

COMMENT ON SCHEMA credentials
    IS 'holds user credentilas, such as username, encrypted passwords and roles';
        

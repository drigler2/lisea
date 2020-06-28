-- authority
CREATE TABLE credentials.authority
(
    id SERIAL PRIMARY KEY,
    authority_name CHARACTER VARYING(127) UNIQUE NOT NULL
);

ALTER TABLE credentials.authority
    OWNER to juno_admin;
COMMENT ON TABLE credentials.authority
    IS 'available authority';

-- user
CREATE TABLE credentials.user
(
    id SERIAL PRIMARY KEY,
    username CHARACTER VARYING(127) UNIQUE NOT NULL,
    password CHARACTER VARYING(127) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER TABLE credentials.user
    OWNER to juno_admin;
COMMENT ON TABLE credentials.user
    IS 'user credentials, user master enable switch, disabled by default';

-- user-authority
CREATE TABLE credentials.user_authority
(
    id_user INTEGER REFERENCES credentials.user(id) NOT NULL,
    id_authority INTEGER REFERENCES credentials.authority(id) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id_user, id_authority)
);

ALTER TABLE credentials.user_authority
    OWNER to juno_admin;
COMMENT ON TABLE credentials.user_authority
    IS 'link between users and authorities, 
       authority enable switch for user, disabled by default';

CREATE TABLE credentials.user_blacklist
(
    id SERIAL PRIMARY KEY,
    id_user INTEGER REFERENCES credentials.user(id) NOT NULL,
    blacklist_reason CHARACTER VARYING(500),
    blacklisted TIMESTAMPTZ,
    whitelisted TIMESTAMPTZ
);

/*

NOTES:
https://stackoverflow.com/questoins/6627289/what-is-the-most-recommended-way-to-store-time-in-postgresql-using-java    


*/

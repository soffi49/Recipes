CREATE TABLE IF NOT EXISTS user
(
    user_id       INT(20)      NOT NULL AUTO_INCREMENT,
    username      VARCHAR(20)  NOT NULL,
    password      VARCHAR(64)  NOT NULL,
    token         VARCHAR(36)  NOT NULL,
    is_admin      BINARY(1)    NOT NULL DEFAULT 0,
    profile_photo VARCHAR(512) NULL,

    CONSTRAINT pk_user
        PRIMARY KEY (user_id),
    CONSTRAINT uk_user
        UNIQUE KEY (token)
) ENGINE = INNODB;
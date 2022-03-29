CREATE TABLE IF NOT EXISTS ingredient
(
    ingredient_id INT(20)     NOT NULL AUTO_INCREMENT,
    name          VARCHAR(40) NOT NULL,

    CONSTRAINT pk_ingredient
        PRIMARY KEY (ingredient_id),
    CONSTRAINT uk_ingredient
        UNIQUE KEY (name)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS recipe
(
    recipe_id    INT(20)     NOT NULL AUTO_INCREMENT,
    name         VARCHAR(50) NOT NULL,
    instructions TEXT        NOT NULL,

    CONSTRAINT pk_recipe
        PRIMARY KEY (recipe_id),
    CONSTRAINT uk_recipe
        UNIQUE KEY (name)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tag
(
    tag_id INT(20)     NOT NULL AUTO_INCREMENT,
    name   VARCHAR(30) NOT NULL,

    CONSTRAINT pk_tag
        PRIMARY KEY (tag_id),
    CONSTRAINT uk_tag
        UNIQUE KEY (name)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS user
(
    user_id  INT(20)     NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(64) NOT NULL,
    token    VARCHAR(36) NOT NULL,
    is_admin BINARY(1)   NOT NULL DEFAULT 0,

    CONSTRAINT pk_user
        PRIMARY KEY (user_id),
    CONSTRAINT uk_user
        UNIQUE KEY (token)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS restaurant
(
    restaurant_id INT(20)     NOT NULL AUTO_INCREMENT,
    name          VARCHAR(80) NOT NULL,

    CONSTRAINT pk_restaurant
        PRIMARY KEY (restaurant_id),
    CONSTRAINT uk_restaurant
        UNIQUE KEY (name)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS shop
(
    shop_id INT(20)     NOT NULL AUTO_INCREMENT,
    name    VARCHAR(80) NOT NULL,

    CONSTRAINT pk_shop
        PRIMARY KEY (shop_id),
    CONSTRAINT uk_shop
        UNIQUE KEY (name)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS recipe_ingredient
(
    recipe_id     INT(20)     NOT NULL,
    ingredient_id INT(20)     NOT NULL,
    quantity      VARCHAR(50) NOT NULL,

    CONSTRAINT fk_recipe_ingredient_recipe
        FOREIGN KEY (recipe_id)
            REFERENCES recipe (recipe_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_recipe_ingredient_ingredient
        FOREIGN KEY (ingredient_id)
            REFERENCES ingredient (ingredient_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT uk_recipe_ingredient
        UNIQUE KEY (recipe_id, ingredient_id)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS recipe_tag
(
    tag_id    INT(20) NOT NULL,
    recipe_id INT(20) NOT NULL,

    CONSTRAINT fk_recipe_tag_recipe
        FOREIGN KEY (recipe_id)
            REFERENCES recipe (recipe_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_recipe_tag_tag
        FOREIGN KEY (tag_id)
            REFERENCES tag (tag_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT uk_recipe_tag
        UNIQUE KEY (recipe_id, tag_id)
) ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS user_recipe
(
    recipe_id INT(20) NOT NULL,
    user_id   INT(20) NOT NULL,

    CONSTRAINT fk_user_recipe_recipe
        FOREIGN KEY (recipe_id)
            REFERENCES recipe (recipe_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_user_recipe_user
        FOREIGN KEY (user_id)
            REFERENCES user (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT uk_user_recipe
        UNIQUE KEY (recipe_id, user_id)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS restaurant_recipe
(
    restaurant_id INT(20) NOT NULL,
    recipe_id     INT(20) NOT NULL,
    price         INT(10) NOT NULL,

    CONSTRAINT fk_restaurant_recipe_recipe
        FOREIGN KEY (recipe_id)
            REFERENCES recipe (recipe_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_restaurant_recipe_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT uk_restaurant_recipe
        UNIQUE KEY (restaurant_id, recipe_id)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS shop_ingredient
(
    shop_id       INT(20) NOT NULL,
    ingredient_id INT(20) NOT NULL,
    price         INT(10) NOT NULL,

    CONSTRAINT fk_shop_ingredient_ingredient
        FOREIGN KEY (ingredient_id)
            REFERENCES ingredient (ingredient_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_shop_ingredient_shop
        FOREIGN KEY (shop_id)
            REFERENCES shop (shop_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT uk_shop_ingredient
        UNIQUE KEY (shop_id, ingredient_id)
) ENGINE = INNODB;

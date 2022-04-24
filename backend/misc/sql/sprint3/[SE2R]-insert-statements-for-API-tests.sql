-- Inserts for ingredient tests --

INSERT INTO ingredient (ingredient_id, name) VALUES (100000, 'duplicatedIngredient');
INSERT INTO ingredient (ingredient_id, name) VALUES (200000, 'toUpdateIngredient');

-- Inserts for recipe tests --

INSERT INTO ingredient (ingredient_id, name) VALUES (300000, 'recipeIngredient1');
INSERT INTO ingredient (ingredient_id, name) VALUES (400000, 'recipeIngredient2');

INSERT INTO recipe (recipe_id, name, instructions) VALUES (100000, 'testRecipe1', 'Test instruction');
INSERT INTO recipe (recipe_id, name, instructions) VALUES (200000, 'testRecipe2', 'Test instruction');
INSERT INTO recipe (recipe_id, name, instructions) VALUES (300000, 'testRecipe3', 'Test instruction');

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (100000, 300000, 'Test quantity');
INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (100000, 400000, 'Test quantity');
INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (200000, 300000, 'Test quantity');

INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1, 100000);
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (2, 300000);
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (3, 300000);

-- Inserts for login tests --

INSERT INTO user (user_id, username, password, token, is_admin) VALUES (100000, 'test username', 'test password', 'test token', 0);

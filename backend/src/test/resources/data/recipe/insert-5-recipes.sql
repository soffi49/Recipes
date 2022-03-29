SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE recipe_ingredient;
TRUNCATE TABLE recipe_tag;
TRUNCATE TABLE ingredient;
TRUNCATE TABLE recipe;
TRUNCATE TABLE tag;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO ingredient (ingredient_id, name) VALUES (1000, 'Ingredient');
INSERT INTO tag (tag_id, name) VALUES (1000, 'vegetarian');

INSERT INTO recipe (recipe_id, name, instructions) VALUES (1, 'Recipe1', 'Test instruction');
INSERT INTO recipe (recipe_id, name, instructions) VALUES (2, 'Recipe2', 'Test instruction');
INSERT INTO recipe (recipe_id, name, instructions) VALUES (3, 'Recipe3', 'Test instruction');
INSERT INTO recipe (recipe_id, name, instructions) VALUES (4, 'Recipe4', 'Test instruction');
INSERT INTO recipe (recipe_id, name, instructions) VALUES (5, 'Recipe5', 'Test instruction');

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (1, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1000, 1);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (2, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1000, 2);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (3, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1000, 3);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (4, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1000, 4);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (5, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1000, 5);
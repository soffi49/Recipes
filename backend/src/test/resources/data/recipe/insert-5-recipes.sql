INSERT INTO ingredient (ingredient_id, name) VALUES (1000, 'Ingredient');

INSERT INTO recipe (recipe_id, name, instructions) VALUES (1, 'Recipe1', 'Test instruction');
INSERT INTO recipe (recipe_id, name, instructions) VALUES (2, 'Recipe2', 'Test instruction');
INSERT INTO recipe (recipe_id, name, instructions) VALUES (3, 'Recipe3', 'Test instruction');
INSERT INTO recipe (recipe_id, name, instructions) VALUES (4, 'Recipe4', 'Test instruction');
INSERT INTO recipe (recipe_id, name, instructions) VALUES (5, 'Recipe5', 'Test instruction');

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (1, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1, 1);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (2, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1, 2);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (3, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1, 3);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (4, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1, 4);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (5, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1, 5);
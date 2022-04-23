INSERT INTO ingredient (ingredient_id, name) VALUES (1000, "Ingredient1");
INSERT INTO ingredient (ingredient_id, name) VALUES (2000, "Ingredient2");

INSERT INTO recipe (recipe_id, name, instructions) VALUES (1000, 'Recipe', 'Test instruction');

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (1000, 1000, '10g');
INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (1000, 2000, '2 slices');
INSERT INTO ingredient (ingredient_id, name) VALUES (1000, 'Ingredient');

INSERT INTO recipe (recipe_id, name, instructions) VALUES (1000, 'Recipe', 'Test instruction');

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity) VALUES (1000, 1000, '10g');
INSERT INTO recipe_tag (tag_id, recipe_id) VALUES (1, 1000);
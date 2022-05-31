SET FOREIGN_KEY_CHECKS = 0;
SET SQL_SAFE_UPDATES = 0;

DELETE FROM ingredient WHERE name in
    ('newIngredient', 'duplicatedIngredient', 'deletedIngredient', 'updatedIngredient', 'toUpdateIngredient', 'recipeIngredient1', 'recipeIngredient2');

DELETE FROM recipe_ingredient WHERE recipe_id in
    (SELECT recipe_id from recipe where name in
    ('testRecipe1', 'testRecipe2', 'testRecipe3', 'newRecipe', 'newRecipeNoTags', 'newRecipeNoIngredients', 'updatedRecipe'));
DELETE FROM recipe_tag WHERE recipe_id in
    (SELECT recipe_id from recipe where name in
    ('testRecipe1', 'testRecipe2', 'testRecipe3', 'newRecipe', 'newRecipeNoTags', 'newRecipeNoIngredients', 'updatedRecipe'));
DELETE FROM recipe WHERE name in
    ('testRecipe1', 'testRecipe2', 'testRecipe3', 'newRecipe', 'newRecipeNoTags', 'newRecipeNoIngredients', 'updatedRecipe');
DELETE FROM recipe WHERE name in
    ('testRecipe1', 'testRecipe2', 'testRecipe3', 'newRecipe', 'newRecipeNoTags', 'newRecipeNoIngredients', 'updatedRecipe');

DELETE FROM user WHERE user_id = 100000;
DELETE FROM user WHERE username in ('newUser', 'duplicatedUser');

DELETE FROM recipe_ingredient WHERE recipe_id = 200000;
DELETE FROM recipe_tag WHERE recipe_id = 200000;
DELETE FROM recipe WHERE recipe_id = 200000;

DELETE FROM recipe_ingredient WHERE recipe_id = 100000;
DELETE FROM recipe_tag WHERE recipe_id = 100000;
DELETE FROM recipe WHERE recipe_id = 100000;

SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = 1;
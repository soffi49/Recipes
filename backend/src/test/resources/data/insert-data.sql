SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE tag;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO tag (tag_id, name) VALUES (1, 'vegetarian');
INSERT INTO tag (tag_id, name) VALUES (2, 'gluten free');
INSERT INTO tag (tag_id, name) VALUES (3, 'low calorie');
INSERT INTO tag (tag_id, name) VALUES (4, 'no lactose');
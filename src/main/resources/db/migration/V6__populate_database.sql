INSERT INTO categories (name)
VALUES ('Fruits'),
       ('Vegetables'),
       ('Dairy'),
       ('Bakery'),
       ('Beverages');
INSERT INTO products (name, price, description, category_id)
VALUES ('Bananas', 1.29, 'Fresh ripe bananas sold per kg', 1),
       ('Apples', 1.99, 'Crisp and juicy red apples, perfect for snacking', 1),
       ('Broccoli', 1.75, 'Green broccoli florets, rich in nutrients', 2),
       ('Carrots', 0.89, 'Organic carrots, sweet and crunchy', 2),
       ('Milk 1L', 1.09, 'Full-fat fresh cow milk in 1-litre bottle', 3),
       ('Cheddar Cheese', 2.49, 'Matured cheddar cheese block, 200g', 3),
       ('Whole Wheat Bread', 1.59, 'Freshly baked whole wheat sandwich bread', 4),
       ('Croissants', 2.99, 'Butter croissants, pack of 4', 4),
       ('Orange Juice 1L', 2.29, '100% pure squeezed orange juice', 5),
       ('Mineral Water 1.5L', 0.69, 'Natural mineral water, 1.5-litre bottle', 5);

BEGIN TRANSACTION;

-- Insert into products table
INSERT INTO products (name, price, description)
VALUES ('Sample Product', 19.99, 'This is a sample product description');

-- Get the newly inserted product ID
SET @new_product_id = LAST_INSERT_ID();

-- Insert into product_details table
INSERT INTO product_details (instructions, guarantee, product_id)
VALUES ('Sample instructions', '30-day guarantee', @new_product_id);

COMMIT TRANSACTION;
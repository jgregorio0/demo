 -- Insert into product_details table
INSERT INTO product_details (id, instructions, guarantee)
VALUES (1, 'Sample instructions', '30-day guarantee');

-- Insert into products table
INSERT INTO products (name, price, description, product_detail_id)
VALUES ('Sample Product', 19.99, 'This is a sample product description',1);

DELETE p, pd
FROM products p
INNER JOIN product_details pd ON p.id = pd.product_id
WHERE p.name = 'Sample Product';
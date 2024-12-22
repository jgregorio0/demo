- Create product

```
curl -X POST \
-H "Content-Type: application/json" \
-d '{
"name": "Example Product",
"price": 9.99,
"description": "A sample product"
}' \
http://localhost:8080/api/products
```

- Get product

```
curl -X GET \
-H "Accept: application/json" \
http://localhost:8080/api/products/1
```

package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductOutputDto> createProduct(@RequestBody ProductInputDto inputDto) {
        return ResponseEntity.ok(productService.createProduct(inputDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOutputDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}

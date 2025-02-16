package demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductOutputDto> create(@RequestBody ProductInputDto input) {
        return ResponseEntity.ok(productService.create(input));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOutputDto> read(@PathVariable Long id) {
        return ResponseEntity.ok(productService.read(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductOutputDto> update(@RequestBody ProductInputDto input, @PathVariable Long id) {
        return ResponseEntity.ok(productService.update(input, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
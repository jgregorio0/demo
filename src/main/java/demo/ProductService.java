package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Transactional
    public ProductOutputDto createProduct(ProductInputDto inputDto) {
        Product product = productMapper.toEntity(inputDto);
        return productMapper.toDto(productRepository.save(product));
    }

    public ProductOutputDto getProductById(Long id) {
        return productMapper.toDto(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("demo.Product not found")));
    }
}

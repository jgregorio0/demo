package demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Transactional
    public ProductOutputDto create(ProductInputDto input) {
        return mapper.mapEntitytoDto(
                repository.save(
                        mapper.mapToEntity(input)));
    }

    public ProductOutputDto read(Long id) {
        return mapper.mapEntitytoDto(
                repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Product not found")));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }


    @Transactional
    public ProductOutputDto update(ProductInputDto input, Long id) {
        return repository.findById(id)
                .map((e) -> {
                    mapper.mapToEntity(e, input);
                    return e;
                })
                .map(repository::save)
                .map(mapper::mapEntitytoDto)
                .orElseThrow(() -> new RuntimeException("Could not uptade product"));
    }
}
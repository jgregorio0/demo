package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private ProductMapper trainerMapper;

    @Transactional
    public ProductOutputDto createProduct(ProductInputDto inputDto) {
        ProductEntity product = trainerMapper.toEntity(inputDto);
        return trainerMapper.toDto(trainerRepository.save(product));
    }

    public ProductOutputDto getTrainer(Long id) {
        return trainerMapper.toDto(trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("demo.Product not found")));
    }

    @Transactional(readOnly = true)
    public ProductWithDetailsDto getProductWithDetailsByName(String name) {
        return trainerMapper.withDetailsDto(trainerRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("demo.Product not found")));
    }
}

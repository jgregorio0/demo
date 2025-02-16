package demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductOutputDto(
        Long id,
        String name,
        BigDecimal price,
        String description,
        String createdBy,
        LocalDateTime createdDate,
        String lastModifiedBy,
        LocalDateTime lastModifiedDate) {
}

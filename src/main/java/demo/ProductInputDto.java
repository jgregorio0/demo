package demo;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductUpdateDto(
        Long id,
        String name,
        BigDecimal price,
        String description) {
}

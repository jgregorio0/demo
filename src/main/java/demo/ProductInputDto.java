package demo;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductInputDto(
        String name,
        BigDecimal price,
        String description) {
}

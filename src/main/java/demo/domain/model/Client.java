package demo.domain.model;

import lombok.Builder;

@Builder
public record Client(
        Long id,
        String name,
        String cif
) {}

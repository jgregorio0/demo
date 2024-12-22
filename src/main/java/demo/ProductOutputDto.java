package demo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductOutputDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
}

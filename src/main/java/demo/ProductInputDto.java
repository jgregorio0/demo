package demo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductInputDto {
    private String name;

    private BigDecimal price;

    private String description;
}

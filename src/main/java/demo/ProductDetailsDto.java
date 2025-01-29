package demo;

import lombok.Data;

import javax.persistence.*;

@Data
public class ProductDetailsDto {
    private Long id;
    private String instructions;
    private String guarantee;

    @OneToOne(mappedBy = "productDetails")
    private ProductEntity product;
}

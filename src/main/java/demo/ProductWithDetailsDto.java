package demo;

import lombok.Data;

@Data
public class ProductWithDetailsDto extends ProductOutputDto {
    private ProductDetailsDto productDetails;
}

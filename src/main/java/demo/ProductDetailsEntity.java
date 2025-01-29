package demo;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product_details")
public class ProductDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String instructions;

    @Column(nullable = false)
    private String guarantee;

    @OneToOne(mappedBy = "productDetails")
    private ProductEntity product;
}

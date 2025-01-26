package demo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
public class ProductDetailsDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String instructions;

    @Column(nullable = false)
    private String garantee;

    @OneToOne(mappedBy = "productDetails")
    private ProductEntity product;
}

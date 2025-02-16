package demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void create() {
        ProductInputDto input = ProductInputDto.builder()
                .price(BigDecimal.ONE)
                .description("Description test")
                .name("Producto 1")
                .build();
        ProductOutputDto product =
                productService.create(
                        input);
        assertThat(product).isNotNull();
        assertThat(product.id()).isNotNull();
        assertThat(product.price()).isEqualTo(input.price());
        assertThat(product.description()).isEqualTo(input.description());
        assertThat(product.name()).isEqualTo(input.name());
        assertThat(product.createdDate()).isNotNull();
        assertThat(product.createdBy()).isEqualTo("SYSTEM");
        assertThat(product.lastModifiedDate()).isNotNull();
        assertThat(product.lastModifiedBy()).isEqualTo("SYSTEM");
    }

    @Test
    void update() {
        ProductInputDto inputCreate = ProductInputDto.builder()
                .price(BigDecimal.ONE)
                .description("Description test")
                .name("Producto 1")
                .build();
        ProductOutputDto created =
                productService.create(
                        inputCreate);

        ProductInputDto inputUpdate = ProductInputDto.builder()
                .price(BigDecimal.TEN)
                .description("Description test 10")
                .name("Producto 10")
                .build();
        ProductOutputDto updated =
                productService.update(
                        inputUpdate, created.id());
        assertThat(updated).isNotNull();
        assertThat(updated.id()).isNotNull();
        assertThat(updated.price()).isEqualTo(inputUpdate.price());
        assertThat(updated.description()).isEqualTo(inputUpdate.description());
        assertThat(updated.name()).isEqualTo(inputUpdate.name());
        assertThat(updated.createdDate()).isNotNull();
        assertThat(updated.createdBy()).isEqualTo("SYSTEM");
        assertThat(updated.lastModifiedDate()).isNotNull();
        assertThat(updated.lastModifiedBy()).isEqualTo("SYSTEM");
    }

}
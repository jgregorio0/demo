package demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductServiceTest {

    String PRODUCT_NAME = "Sample Product";

    @Autowired
    TrainerService productService;

    @Test
    @Sql(value = "classpath:insert_product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:delete_product.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test() {
        ProductOutputDto product = productService.getTrainer(PRODUCT_NAME);
        assertNotNull(product);
    }

    @Test
    @Sql(value = "classpath:insert_product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:delete_product.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testWithDetails() {
        ProductWithDetailsDto product = productService.getProductWithDetailsByName(PRODUCT_NAME);
        assertNotNull(product);
        assertNotNull(product.getProductDetails());
    }

}
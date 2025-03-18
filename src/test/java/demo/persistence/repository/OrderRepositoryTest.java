package demo.persistence.repository;

import demo.persistence.model.ClientEntity;
import demo.persistence.model.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderJpaRepository orderJpaRepository;
    @Autowired
    ClientJpaRepository clientJpaRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    void givenOrderWithoutClient_whenSave_ThenSuccess() {
        OrderEntity order = OrderEntity.builder()
                .name("Order 1")
                .build();
        OrderEntity saved = orderJpaRepository.saveAndFlush(order);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isNotNull();
        assertThat(saved.getClient()).isNull();
    }

    @Test
    @Transactional
    @Sql("/createClient.sql")
    void givenOrderWithExistingClient_whenSave_ThenSavedIncludingClientReference() {
        long existingClientId = 1L;
        // Get Proxy ClientEntity
        ClientEntity proxyClient = clientJpaRepository.getReferenceById(existingClientId);
        ClientEntity clientEntity = ClientEntity.builder()
                .id(proxyClient.getId())
                .secondId(proxyClient.getSecondId())
                .build();
        OrderEntity order = OrderEntity.builder()
                .name("Order 1")
                // Do not use a proxy to establish a foreign key to another entity that is referenced by a column other than the 'id' field
                // .client(proxyClient)
                // Use an ClientEntity and ensure it is not proxy
                .client(clientEntity)
                .build();
        OrderEntity saved = orderJpaRepository.saveAndFlush(order);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isNotNull();
        assertThat(saved.getClient()).isNotNull();
    }


}
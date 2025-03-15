package demo.persistence.repository;

import demo.persistence.model.GroupEntity;
import demo.persistence.model.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
class GroupRepositoryImplTest {

    @Autowired
    GroupJpaRepository groupJpaRepository;

    @Test
    @Transactional
    void createUnidirectionalOneToMany() {
        GroupEntity groupEntity =
                GroupEntity.builder()
                        .number(1)
                        .build();
        groupEntity.getOrders()
                .addAll(
                        List.of(
                                buildOrder("Order 1"),
                                buildOrder("Order 2"),
                                buildOrder("Order 3")));
        entityManager.persist(groupEntity);
        /*
        insert
    into
        groups
        (id, number)
    values
        (default, ?)
2025-03-13 23:42:20.174 TRACE 18921 --- [    Test worker] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [INTEGER] - [1]


    insert
    into
        orders
        (id, name)
    values
        (default, ?)
2025-03-13 23:42:20.183 TRACE 18921 --- [    Test worker] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [Order 1]


    insert
    into
        orders
        (id, name)
    values
        (default, ?)
2025-03-13 23:42:20.185 TRACE 18921 --- [    Test worker] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [Order 2]

    insert
    into
        orders
        (id, name)
    values
        (default, ?)
2025-03-13 23:42:20.185 TRACE 18921 --- [    Test worker] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [Order 3]

        */
    }

    private static OrderEntity buildOrder(String name) {
        return OrderEntity.builder().name(name).build();
    }

    @Test
    void readUnidirectionalOneToMany() {


    }
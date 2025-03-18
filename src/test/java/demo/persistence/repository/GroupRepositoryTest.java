package demo.persistence.repository;

import demo.persistence.model.GroupEntity;
import demo.persistence.model.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class GroupRepositoryTest {

    @Autowired
    GroupJpaRepository groupJpaRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    void create() {
        GroupEntity groupEntity =
                GroupEntity.builder()
                        .number(1)
                        .build();
        groupEntity.getOrders()
                .addAll(
                        List.of(
                                OrderEntity.builder().name("Order 1").build(),
                                OrderEntity.builder().name("Order 2").build(),
                                OrderEntity.builder().name("Order 3").build()));
        groupJpaRepository.saveAndFlush(groupEntity);
    }

    @Test
    @Transactional(readOnly = true)
    @Sql("/createUnidirectionalOneToMany.sql")
    void findById() {
        Optional<GroupEntity> byId = groupJpaRepository.findById(1L);
//        select groupentit0_.id as id1_0_0_, groupentit0_.number as number2_0_0_ from groups groupentit0_ where groupentit0_.id=?
    }

    @Test
    @Transactional(readOnly = true)
    @Sql("/createUnidirectionalOneToMany.sql")
    void findWithOrdersById() {
        Optional<GroupEntity> byId = groupJpaRepository.findWithOrdersById(1L);
//        select groupentit0_.id as id1_0_0_, orderentit2_.id as id1_2_1_, groupentit0_.number as number2_0_0_, orderentit2_.name as name2_2_1_, orders1_.group_entity_id as group_en1_1_0__, orders1_.orders_id as orders_i2_1_0__
//        from groups groupentit0_
//        left outer join groups_orders orders1_ on groupentit0_.id=orders1_.group_entity_id
//        left outer join orders orderentit2_ on orders1_.orders_id=orderentit2_.id
//        where groupentit0_.id=?
    }

    @Test
    @Transactional(readOnly = true)
    @Sql("/createUnidirectionalOneToMany.sql")
    void update() {
        GroupEntity group = groupJpaRepository.findWithOrdersById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        group.setNumber(2);
        group.getOrders().remove(OrderEntity.builder().id(1L).build());
        group.getOrders().add(OrderEntity.builder().name("Order 4").build());
        groupJpaRepository.saveAndFlush(group);
//        select groupentit0_.id as id1_0_0_, orderentit2_.id as id1_2_1_, groupentit0_.number as number2_0_0_, orderentit2_.name as name2_2_1_, orders1_.group_entity_id as group_en1_1_0__, orders1_.orders_id as orders_i2_1_0__
//        from groups groupentit0_
//        left outer join groups_orders orders1_ on groupentit0_.id=orders1_.group_entity_id
//        left outer join orders orderentit2_ on orders1_.orders_id=orderentit2_.id
//        where groupentit0_.id=?
    }
}
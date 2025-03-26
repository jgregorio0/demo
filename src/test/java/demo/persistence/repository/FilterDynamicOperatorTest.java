package demo.persistence.repository;

import demo.persistence.model.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class FilterDynamicOperatorTest {

    @Autowired
    OrderJpaRepository orderJpaRepository;

    @Test
    @Sql("/ordersToFindUsingDynamicOperator.sql")
    void givenOrders_whenFindOrdersUngroupedOrGroupedBy1_ThenFoundUngroupedAndGroupedBy1(){
        Long groupedBy1OrderId = 1L;
        Long groupedByGroup2OrderId = 2L;
        Long ungroupedOrderId = 4L;
        Boolean groupedFilter = false;
        Long groupedByIdFilter = 1L;
        String operatorFilter = "OR";
        // find orders NOT grouped (groupId is NULL) OR grouped by id 1L
        List<OrderEntity> ordersByFilter =
                orderJpaRepository.findOrdersByFilter(
                        groupedFilter, groupedByIdFilter, operatorFilter);
        Set<Long> foundOrderIds = ordersByFilter.stream()
                .map(OrderEntity::getId)
                .collect(Collectors.toSet());
        assertThat(foundOrderIds).contains(groupedBy1OrderId);
        assertThat(foundOrderIds).contains(ungroupedOrderId);
        assertThat(foundOrderIds).doesNotContain(groupedByGroup2OrderId);
    }
}

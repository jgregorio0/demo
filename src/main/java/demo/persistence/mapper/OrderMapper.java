package demo.persistence.mapper;

import demo.api.StudentMapper;
import demo.domain.model.Order;
import demo.persistence.model.OrderEntity;
import demo.persistence.model.OrderGroupEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring",
        uses = {ClientMapper.class, StudentMapper.class})
public interface OrderMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "billingClient", source = "client")
    Order mapOrderEntityToOrder(OrderEntity entity);

    default Order mapOrderGroupToOrder(OrderGroupEntity orderGroupEntity) {
        if (Objects.isNull(orderGroupEntity)) {
            return null;
        }
        return mapOrderEntityToOrder(orderGroupEntity.getOrder());
    }

    default List<Order> mapOrderGroupsToOrders(List<OrderGroupEntity> orderGroupEntities) {
        if (Objects.isNull(orderGroupEntities)) {
            return null;
        }
        return orderGroupEntities.stream()
                .map(this::mapOrderGroupToOrder)
                .toList();
    }

}

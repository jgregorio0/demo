package demo.persistence.repository;

import demo.domain.GroupRepository;
import demo.domain.exception.DomainException;
import demo.domain.model.Group;
import demo.domain.model.request.GroupRequest;
import demo.persistence.mapper.GroupMapper;
import demo.persistence.model.GroupEntity;
import demo.persistence.model.OrderEntity;
import demo.persistence.model.OrderGroupEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class GroupRepositoryImpl implements GroupRepository {
    private final GroupJpaRepository groupJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final OrderGroupJpaRepository orderGroupJpaRepository;
    private final GroupMapper groupMapper;

    @Override
    public Group readWithOrdersAndStudents(Long id) {
        // find group with orders
        GroupEntity groupWithOrders = groupJpaRepository.findWithOrdersById(id)
                .orElseThrow(() -> new DomainException("Group not found"));
        // find order with students
        Set<Long> orderIds = groupWithOrders
                .getOrderGroups().stream()
                .map(OrderGroupEntity::getOrder)
                .map(OrderEntity::getId)
                .collect(Collectors.toSet());
        orderJpaRepository.findWithStudentsByIds(orderIds);
        // map groups with orders and students
        return groupMapper.toDto(groupWithOrders);
    }

    @Override
    public Group create(GroupRequest groupRequest) {
        return groupMapper.toDto(
                groupJpaRepository.save(
                        groupMapper.toEntity(groupRequest)));
    }

    @Override
    public void delete(Long id) {
        groupJpaRepository.deleteById(id);
    }

    @Override
    public Group update(Long id, GroupRequest input) {
        return groupJpaRepository.findById(id)
                .map(group -> {
                    // update group orders
                    buildOrderGroups(group.getId(), input.orderIds())
                            .map(orderGroupJpaRepository::saveAll)
                            .ifPresent(group::setOrderGroups);
                    // update group
                    groupMapper.update(group, input);
                    return groupJpaRepository.save(group);
                })
                .map(groupMapper::toDto)
                .orElseThrow(() -> new DomainException("Group not found"));
    }

    private Optional<List<OrderGroupEntity>> buildOrderGroups(Long groupId, List<Long> orderIds) {
        if (Objects.isNull(groupId)) {
            return Optional.empty();
        }
        if (Objects.isNull(orderIds)) {
            return Optional.empty();
        }
        return Optional.of(
                orderIds.stream()
                        .map(orderId -> OrderGroupEntity.builder()
                                .group(GroupEntity.builder()
                                        .id(groupId)
                                        .build())
                                .order(OrderEntity.builder()
                                        .id(orderId)
                                        .build())
                                .build())
                        .toList());
    }
}

package demo.infrastructure.persistence.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import demo.domain.GroupRepository;
import demo.domain.exception.DomainException;
import demo.domain.model.Group;
import demo.domain.model.request.GroupRequest;
import demo.infrastructure.persistence.mapper.GroupMapper;
import demo.infrastructure.persistence.model.GroupEntity;
import demo.infrastructure.persistence.model.OrderEntity;
import demo.infrastructure.persistence.model.OrderGroupEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class GroupRepositoryImpl implements GroupRepository {

  private final GroupJpaRepository groupJpaRepository;

  private final OrderJpaRepository orderJpaRepository;

  private final OrderGroupJpaRepository orderGroupJpaRepository;

  private final GroupMapper groupMapper;

  @Override
  public Group read(final Long id) throws DomainException {
    final GroupEntity entity =
        groupJpaRepository.findById(id)
                          .orElseThrow(() -> new DomainException("Group not found"));
    return groupMapper.mapGroupEntityToGroup(entity);
  }

  @Override
  public Group readWithOrders(Long id) {
    // find group with orders
    final GroupEntity group =
        groupJpaRepository.findWithOrdersById(id)
                          .orElseThrow(() -> new DomainException("Group not found"));
    return groupMapper.mapGroupEntityToGroupWithOrders(group);
  }

  @Override
  public Group create(GroupRequest groupRequest) {
    return groupMapper.mapGroupEntityToGroup(
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
                             .map(groupMapper::mapGroupEntityToGroup)
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

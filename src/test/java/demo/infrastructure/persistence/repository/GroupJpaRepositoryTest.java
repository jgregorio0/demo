package demo.infrastructure.persistence.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

import demo.infrastructure.persistence.model.ClientEntity;
import demo.infrastructure.persistence.model.GroupEntity;
import demo.infrastructure.persistence.repository.GroupJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
class GroupJpaRepositoryTest {

  @Autowired
  GroupJpaRepository repository;

  @Test
  @Sql("/group/service/group5And6WithClient5And6.sql")
  void givenClient5And6_whenSaveAllGroup5WithClient5AndGroup6WithClient6_thenReturn() {
    // GIVEN clients
    long CLIENT_5 = 5L;
    long CLIENT_6 = 6L;
    // WHEN save groups including clients
    String GROUP_NUMBER_5 = "5";
    String GROUP_NUMBER_6 = "6";
    final List<GroupEntity> groups = List.of(
        buildGroup(GROUP_NUMBER_5, CLIENT_5),
        buildGroup(GROUP_NUMBER_6, CLIENT_6));
    final List<GroupEntity> savedGroups = repository.saveAll(groups);
    // AND find groups related with clients EAGERLY
    final Set<Long> groupIds = savedGroups.stream().map(GroupEntity::getId).collect(Collectors.toSet());
    final List<GroupEntity> foundGroups = repository.findAllById(groupIds);
    // THEN saved groups return entity NOT including related entities
    assertThat(savedGroups).isNotEmpty();
    assertThat(savedGroups.get(0).getClient()).isNotNull();
    assertThat(savedGroups.get(0).getClient().getId()).isNotNull();
    assertThat(savedGroups.get(0).getClient().getCif()).isNull();
    // AND found groups return entity including related entities
    assertThat(foundGroups).isNotEmpty();
    assertThat(foundGroups.get(0).getClient()).isNotNull();
    assertThat(foundGroups.get(0).getClient().getId()).isNotNull();
    assertThat(foundGroups.get(0).getClient().getCif()).isNotNull();
    assertThat(foundGroups.get(0).getClient().getName()).isNotNull();
  }

  private static GroupEntity buildGroup(final String groupNumber, final long groupClientId) {
    return GroupEntity.builder()
                      .number(groupNumber)
                      .client(buildClient(groupClientId))
                      .build();
  }

  private static ClientEntity buildClient(final long clientId) {
    return ClientEntity.builder()
                       .id(clientId)
                       .build();
  }
}
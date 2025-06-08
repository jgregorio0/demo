import demo.domain.GroupService;
import demo.domain.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class GroupServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private GroupService groupService;


    private String createGroupNumber(Long id) {
        return "Group " + id;
    }

    @Test
    void testParallelGroupNumberRetrieval() {
        Group group1 = Group.builder()
                .id(1L)
                .number(createGroupNumber(1L))
                .build();
        Group group2 = Group.builder()
                .id(2L)
                .number(createGroupNumber(2L))
                .build();

        // Mock responses
        when(restTemplate.getForObject("/groups", List.class))
                .thenReturn(List.of(group1, group2));

        when(restTemplate.getForObject("/group-number/{id}", String.class, group1.getId()))
                .thenReturn(group1.getNumber());
        when(restTemplate.getForObject("/group-number/{id}", String.class, group2.getId()))
                .thenReturn(group2.getNumber());

        // Execute test
        List<Group> result = groupService.getGroupsWithNumbers();

        assertEquals(2, result.size());
        assertTrue(result.stream()
                .anyMatch(g -> g.getId().equals(group1.getId()) && g.getNumber().equals(group1.getNumber())));
        assertTrue(result.stream()
                .anyMatch(g -> g.getId().equals(group2.getId()) && g.getNumber().equals(group2.getNumber())));
    }

    @Test
    void testRaceConditionWithSlowResponse() {
        // Setup test data
        int groupsSize = 100;
        List<Group> groups = LongStream.rangeClosed(1, groupsSize)
                .mapToObj(id -> Group.builder()
                        .id(id)
                        .number(createGroupNumber(id))
                        .build()).toList();
        Group group1 = Group.builder()
                .id(1L)
                .number(createGroupNumber(1L))
                .build();


        when(restTemplate.getForObject("/groups", List.class))
                .thenReturn(Collections.singletonList(groups));

        when(restTemplate.getForObject("/group-number/{id}", String.class, group1.getId()))
                .thenAnswer(invocation -> {
                    try {
                        Thread.sleep(groupsSize); // Simulate slow response
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return group1.getNumber();
                });
        LongStream.rangeClosed(2, groupsSize).forEach(id -> {
            when(restTemplate.getForObject("/group-number/{id}", String.class, id))
                    .thenAnswer(invocation -> {
                        // fast response
                        return createGroupNumber(id);
                    });
        });

        // Execute test
        List<Group> result = groupService.getGroupsWithNumbers();
        Map<Long, List<Group>> map = result.stream().collect(Collectors.groupingBy(Group::getId));

        assertEquals(groupsSize, result.size());
        assertEquals(group1.getId(), map.get(group1.getId()).get(0).getId());
        assertEquals(group1.getNumber(), map.get(group1.getId()).get(0).getNumber());
    }
}
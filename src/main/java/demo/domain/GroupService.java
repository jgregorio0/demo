package demo.domain;

import demo.api.ApiGroupNumberRepository;
import demo.domain.model.Group;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class GroupService {
    private final ApiGroupRepository groupRepository;
    private final ApiGroupNumberRepository groupNumberRepository;

    public List<Group> getGroupsWithNumbers() {
        List<Group> groups = groupRepository.getGroups();
        return getGroupsWithNumber(groups);
    }

    private List<Group> getGroupsWithNumber(List<Group> groups) {
        List<CompletableFuture<Group>> futures = groups.stream()
                .map(group -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return group.toBuilder()
                                .number(groupNumberRepository.getGroupNumber(group.getId()))
                                .build();
                    } catch (RestClientException e) {
                        log.error("Failed to fetch group number for group {}: {}",
                                group.getId(), e.getMessage());
                        return group;
                    }
                }))
                .toList();
        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}

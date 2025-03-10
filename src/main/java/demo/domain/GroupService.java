package demo.domain;

import demo.domain.model.Group;
import demo.domain.model.request.GroupRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    public Group create(GroupRequest groupRequest) {
        return groupRepository.create(groupRequest);
    }

    @Transactional(readOnly = true)
    public Group read(Long id) {
        return groupRepository.readWithOrdersAndStudents(id);
    }

    @Transactional
    public void delete(Long id) {
        groupRepository.delete(id);
    }

    @Transactional
    public Group update(Long id, GroupRequest input) {
        validateUpdate(id, input);
        return groupRepository.update(id, input);
    }

    private void validateUpdate(Long id, GroupRequest input) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Group id must not be null");
        }
        if (Objects.isNull(input)) {
            throw new IllegalArgumentException("Group request input must not be null");
        }
    }
}

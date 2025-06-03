package demo.domain;

import demo.domain.model.DiplomaStudent;
import demo.domain.model.ElearningStudent;
import demo.domain.model.Group;
import demo.domain.model.Order;
import demo.domain.model.Student;
import demo.domain.model.request.GroupRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public Group create(GroupRequest groupRequest) {
        return groupRepository.create(groupRequest);
    }

    @Transactional(readOnly = true)
    public Group read(Long id) {
        return groupRepository.read(id);
    }

    @Transactional(readOnly = true)
    public Group readWithOrders(Long id) {
        return groupRepository.readWithOrders(id);
    }

    @Transactional(readOnly = true)
    public Group readWithOrdersAndStudents(Long id) {
        return addStudentsToOrders(
                groupRepository.readWithOrders(id));
    }

    @Transactional(readOnly = true)
    public Group readWithOrdersAndDiplomaStudents(final Long id) {
        return addDiplomaStudentsToOrders(
                groupRepository.readWithOrders(id));
    }

    @Transactional(readOnly = true)
    public Group readWithOrdersAndElearningStudents(final Long id) {
        return addElearningStudentsToOrders(
                groupRepository.readWithOrders(id));
    }

    private Group addDiplomaStudentsToOrders(final Group group) {
        // add diploma students to group orders
        Set<Long> orderIds = group.getOrders().stream()
                                  .map(Order::getId)
                                  .collect(Collectors.toSet());
        final Map<Long, List<DiplomaStudent>> orderStudents = studentRepository.readOrderIdWithDiplomaStudentsByOrderIds(orderIds);
        group.getOrders().forEach(o -> o.setStudents(orderStudents.get(o.getId())));
        return group;
    }

    private Group addElearningStudentsToOrders(final Group group) {
        // add diploma students to group orders
        Set<Long> orderIds = group.getOrders().stream()
                                  .map(Order::getId)
                                  .collect(Collectors.toSet());
        final Map<Long, List<ElearningStudent>> orderStudents = studentRepository.readOrderIdWithElearningStudentsByOrderIds(orderIds);
        group.getOrders().forEach(o -> o.setStudents(orderStudents.get(o.getId())));
        return group;
    }

    private Group addStudentsToOrders(final Group group) {
        final Set<Long> orderIds = group.getOrders().stream()
                                        .map(Order::getId)
                                        .collect(Collectors.toSet());
        final Map<Long, List<Student>> orderStudents = studentRepository.readOrderIdWithStudentsByOrderIds(orderIds);
        group.getOrders().forEach(o -> o.setStudents(orderStudents.get(o.getId())));
        return group;
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

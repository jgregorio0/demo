package demo.domain;

import demo.domain.exception.DomainException;
import demo.domain.model.Group;
import demo.domain.model.request.GroupRequest;

import java.util.Optional;

public interface GroupRepository {

    Group readWithOrdersAndStudents(Long id) throws DomainException;

    Group create(GroupRequest groupRequest);

    void delete(Long id);

    Group update(Long id, GroupRequest input);
}

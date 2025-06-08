package demo.domain;

import demo.domain.model.Group;

import java.util.List;

public interface ApiGroupRepository {

    List<Group> getGroups();
}

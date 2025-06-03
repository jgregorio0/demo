package demo.persistence.mapper;

import demo.domain.model.request.GroupRequest;
import demo.domain.model.Group;
import demo.persistence.model.GroupEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {OrderMapper.class, ClientMapper.class})
public interface GroupMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "number")
    @Mapping(target = "client")
    Group mapGroupEntityToGroup(GroupEntity entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "number")
    @Mapping(target = "client")
    @Mapping(target = "orders", source = "orderGroups")
    Group mapGroupEntityToGroupWithOrders(GroupEntity entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "number")
    @Mapping(target = "client", source = "clientId")
    GroupEntity toEntity(GroupRequest dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "number")
    @Mapping(target = "client", source = "clientId")
    void update(@MappingTarget GroupEntity groupEntity, GroupRequest dto);

}

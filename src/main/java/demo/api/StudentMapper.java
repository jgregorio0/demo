package demo.api;

import demo.domain.model.Student;
import demo.persistence.model.OrderStudentEntity;
import demo.persistence.model.StudentEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "surname")
    @Mapping(target = "nif")
    Student toDto(StudentEntity entity);

    default Student mapOrderStudentToStudent(OrderStudentEntity orderStudentEntity) {
        if (Objects.isNull(orderStudentEntity)) {
            return null;
        }
        return toDto(orderStudentEntity.getStudent());
    }

    default List<Student> mapOrderStudentsToStudent(List<OrderStudentEntity> orderStudentEntities) {
        if (Objects.isNull(orderStudentEntities)) {
            return null;
        }
        return orderStudentEntities.stream()
                .map(this::mapOrderStudentToStudent)
                .toList();
    }
}

package demo.infrastructure.api;

import demo.domain.model.DiplomaStudent;
import demo.domain.model.ElearningStudent;
import demo.domain.model.Student;
import demo.infrastructure.persistence.model.DiplomaStudentEntity;
import demo.infrastructure.persistence.model.ElearningStudentEntity;
import demo.infrastructure.persistence.model.StudentEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMapper {

  // initialize StudentMapper
  StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);



  Student mapStudentEntityToStudent(StudentEntity entity);

  @BeanMapping(builder = @org.mapstruct.Builder(disableBuilder = true))
  @Mapping(source = "student.id", target = "id")
  @Mapping(source = "student.name", target = "name")
  @Mapping(source = "student.surname", target = "surname")
  @Mapping(source = "student.nif", target = "nif")
  @Mapping(source = "elearning.integratedDate", target = "integratedDate")
  ElearningStudent mapStudentEntityToElearningStudent(StudentEntity student, ElearningStudentEntity elearning);

  @BeanMapping(builder = @org.mapstruct.Builder(disableBuilder = true))
  @Mapping(source = "student.id", target = "id")
  @Mapping(source = "student.name", target = "name")
  @Mapping(source = "student.surname", target = "surname")
  @Mapping(source = "student.nif", target = "nif")
  @Mapping(source = "diploma.diplomaDate", target = "diplomaDate")
  DiplomaStudent mapStudentEntityToDiplomaStudent(StudentEntity student, DiplomaStudentEntity diploma);
}

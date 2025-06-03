package demo.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

import demo.domain.exception.DomainException;
import demo.domain.model.DiplomaStudent;
import demo.domain.model.ElearningStudent;
import demo.domain.model.Student;

public interface StudentRepository {

  Map<Long, List<Student>> readOrderIdWithStudentsByOrderIds(Set<Long> ids) throws DomainException;

  Map<Long, List<ElearningStudent>> readOrderIdWithElearningStudentsByOrderIds(Set<Long> ids) throws DomainException;

  Map<Long, List<DiplomaStudent>> readOrderIdWithDiplomaStudentsByOrderIds(Set<Long> ids) throws DomainException;
}

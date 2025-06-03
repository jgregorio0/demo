package demo.infrastructure.persistence.projection;

import demo.infrastructure.persistence.model.DiplomaStudentEntity;
import demo.infrastructure.persistence.model.StudentEntity;

public interface OrderDiplomaStudentProjection {
  Long getOrderId();
  StudentEntity getStudent();
  DiplomaStudentEntity getDiploma();
}

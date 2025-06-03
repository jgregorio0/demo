package demo.persistence.projection;

import demo.persistence.model.DiplomaStudentEntity;
import demo.persistence.model.StudentEntity;

public interface OrderDiplomaStudentProjection {
  Long getOrderId();
  StudentEntity getStudent();
  DiplomaStudentEntity getDiploma();
}

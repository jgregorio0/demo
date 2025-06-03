package demo.infrastructure.persistence.projection;

import demo.infrastructure.persistence.model.ElearningStudentEntity;
import demo.infrastructure.persistence.model.StudentEntity;

public interface OrderElarningStudentProjection {
  Long getOrderId();
  StudentEntity getStudent();
  ElearningStudentEntity getElearning();
}

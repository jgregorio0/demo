package demo.persistence.projection;

import demo.persistence.model.ElearningStudentEntity;
import demo.persistence.model.StudentEntity;

public interface OrderElarningStudentProjection {
  Long getOrderId();
  StudentEntity getStudent();
  ElearningStudentEntity getElearning();
}

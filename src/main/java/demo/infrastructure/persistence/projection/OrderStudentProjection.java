package demo.infrastructure.persistence.projection;

import demo.infrastructure.persistence.model.StudentEntity;

public interface OrderStudentProjection {
  Long getOrderId();
  StudentEntity getStudent();
}

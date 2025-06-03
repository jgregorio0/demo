package demo.persistence.projection;

import demo.persistence.model.StudentEntity;

public interface OrderStudentProjection {
  Long getOrderId();
  StudentEntity getStudent();
}

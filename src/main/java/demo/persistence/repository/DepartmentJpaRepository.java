package demo.persistence.repository;

import demo.persistence.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentJpaRepository extends JpaRepository<Department, Long> {

}

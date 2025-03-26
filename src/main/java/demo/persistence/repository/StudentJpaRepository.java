package demo.persistence.repository;

import demo.persistence.model.School;
import demo.persistence.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {

}

package demo.persistence.repository;

import demo.persistence.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolJpaRepository extends JpaRepository<School, Long> {

}

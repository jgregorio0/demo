package demo.persistence.repository;

import demo.persistence.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonJpaRepository extends JpaRepository<Person, Long> {

}

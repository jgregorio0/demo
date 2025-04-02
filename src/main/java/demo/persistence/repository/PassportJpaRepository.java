package demo.persistence.repository;

import demo.persistence.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportJpaRepository extends JpaRepository<Passport, Long> {

}

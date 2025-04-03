package demo.persistence.repository;

import demo.persistence.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {

}

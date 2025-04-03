package demo.persistence.repository;

import demo.persistence.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

}

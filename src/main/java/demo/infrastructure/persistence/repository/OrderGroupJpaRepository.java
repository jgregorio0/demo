package demo.infrastructure.persistence.repository;

import demo.infrastructure.persistence.model.OrderGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderGroupJpaRepository extends JpaRepository<OrderGroupEntity, Long> {
}

package demo.persistence.repository;

import demo.persistence.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupJpaRepository extends JpaRepository<GroupEntity, Long> {

    @Query("""
            SELECT g
            FROM GroupEntity g
                LEFT JOIN FETCH g.orders o
            WHERE g.id = :id
            """)
    Optional<GroupEntity> findWithOrdersById(Long id);
}

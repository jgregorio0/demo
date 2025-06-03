package demo.infrastructure.persistence.repository;

import demo.infrastructure.persistence.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    @Query("""
            SELECT o
            FROM OrderEntity o
                LEFT JOIN FETCH o.client c
                LEFT JOIN FETCH o.orderStudents os
                LEFT JOIN FETCH os.student s
            WHERE o.id IN :ids
            """)
    List<OrderEntity> findWithStudentsByIds(Set<Long> ids);

}

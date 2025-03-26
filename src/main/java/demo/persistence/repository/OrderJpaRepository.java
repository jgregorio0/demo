package demo.persistence.repository;

import demo.persistence.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    @Query("""
            SELECT o
            FROM OrderEntity o
                LEFT JOIN GroupOrderEntity go ON o.id = go.id.orderId
            WHERE (
                :operator = 'AND' AND (
                    (
                        :grouped IS NULL OR (
                            :grouped = FALSE AND
                             go.id.groupId IS NULL
                        )
                        OR (
                            :grouped = TRUE AND
                             go.id.groupId IS NOT NULL
                        )
                    ) AND (
                        :groupId IS NULL
                            OR :groupId = go.id.groupId
                    )
                )
            ) OR (
                :operator = 'OR' AND (
                    (
                        :grouped IS NULL OR (
                            :grouped = FALSE AND
                             go.id.groupId IS NULL
                        )
                        OR (
                            :grouped = TRUE AND
                             go.id.groupId IS NOT NULL
                        )
                    )
                    OR (
                        :groupId IS NULL OR
                            :groupId = go.id.groupId
                    )
                )
            ) OR (
                :operator IS NULL AND
                :grouped IS NULL AND
                :groupId IS NULL
            )
            """)
    List<OrderEntity> findOrdersByFilter(Boolean grouped, Long groupId, String operator);
}

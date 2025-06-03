package demo.persistence.repository;

import java.util.List;
import java.util.Set;

import demo.persistence.model.StudentEntity;
import demo.persistence.projection.OrderDiplomaStudentProjection;
import demo.persistence.projection.OrderElarningStudentProjection;
import demo.persistence.projection.OrderStudentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentEntity, Long> {

    @Query("""
        SELECT
          os.order.id as orderId,
          s as student
        FROM StudentEntity s
          JOIN OrderStudentEntity os ON os.student.id = s.id
        WHERE os.order.id IN :orderIds
        """)
    List<OrderStudentProjection> findOrderStudentByOrderIds(Set<Long> orderIds);

    @Query("""
        SELECT
          os.order.id as orderId,
          s as student,
          se as elarning
        FROM StudentEntity s
          JOIN ElearningStudentEntity se ON s.id = se.id
          JOIN OrderStudentEntity os ON os.student.id = s.id
        WHERE os.order.id IN :orderIds
        """)
    List<OrderElarningStudentProjection> findOrderElearningStudentByOrderIds(Set<Long> orderIds);

    @Query("""
        SELECT
          os.order.id as orderId,
          s as student,
          sd as diploma
        FROM StudentEntity s
          JOIN DiplomaStudentEntity sd ON s.id = sd.id
          JOIN OrderStudentEntity os ON os.student.id = s.id
        WHERE os.order.id IN :orderIds
        """)
    List<OrderDiplomaStudentProjection> findOrderDiplomaStudentByOrderIds(Set<Long> orderIds);

}

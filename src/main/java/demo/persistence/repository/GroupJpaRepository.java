package demo.persistence.repository;

import demo.persistence.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupJpaRepository extends JpaRepository<GroupEntity, Long> {

    // Use FETCH to initialize entity fields. Otherwise, a separate query will be generated for each unfetched field.
    @Query("""
            SELECT g
            FROM GroupEntity g
                LEFT JOIN FETCH g.client c
                LEFT JOIN FETCH g.orderGroups og
                LEFT JOIN FETCH og.order o
                LEFT JOIN FETCH o.client oc
            WHERE g.id = :id
            """)
    Optional<GroupEntity> findWithOrdersById(Long id);

    //  throws MultipleBagFetchException because groups OneToMany orders OneToMany students
//    @Query("""
//            SELECT g
//            FROM GroupEntity g
//                LEFT JOIN FETCH g.client c
//                LEFT JOIN FETCH g.orderGroups og
//                LEFT JOIN FETCH og.order o
//                LEFT JOIN FETCH o.client oc
//                LEFT JOIN FETCH o.orderStudents os
//                LEFT JOIN FETCH os.student s
//            """)
//    Optional<GroupEntity> findWithOrdersById(Long id);

}

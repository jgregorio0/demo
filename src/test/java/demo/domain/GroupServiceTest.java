package demo.domain;

import demo.domain.exception.DomainException;
import demo.domain.model.Group;
import demo.domain.model.Order;
import demo.domain.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Test
    void givenNotExistingGroup_whenRead_thenEntityNotFoundException() {
        long GROUP_ID = 0L;
        Executable read = () -> groupService.read(GROUP_ID);
        assertThrows(DomainException.class, read);
    }

    @Test
    @Sql("/group/service/group5WithClientNullAndOrdersEmpty.sql")
    void givenGroup5WithClientNullAndOrdersEmpty_whenRead_thenReturn() {
        long GROUP_ID = 5L;
        Group read = groupService.read(GROUP_ID);
        assertThat(read.id()).isEqualTo(GROUP_ID);
        assertThat(read.client()).isNull();
        assertThat(read.orders()).isEmpty();
    }

    @Test
    @Sql("/group/service/group1WithClient1AndOrdersEmpty.sql")
    void givenGroup1WithClient1AndOrdersEmpty_whenRead_thenReturn() {
        long GROUP_ID = 1L;
        long CLIENT_ID = 1L;
        Group read = groupService.read(GROUP_ID);
        assertThat(read.id()).isEqualTo(GROUP_ID);
        assertThat(read.client().id()).isEqualTo(CLIENT_ID);
        assertThat(read.orders()).isEmpty();
    }

    @Test
    @Sql("/group/service/group2WithClient2AndOrders1AndStudentsEmpty.sql")
    void givenGroup2WithClient2AndOrders1AndStudentsEmpty_whenRead_thenReturn() {
        long GROUP_ID = 2L;
        long CLIENT_ID = 2L;
        long ORDER_ID = 1L;
        Group read = groupService.read(GROUP_ID);
        assertThat(read.id()).isEqualTo(GROUP_ID);
        assertThat(read.client().id()).isEqualTo(CLIENT_ID);
        assertThat(read.orders().get(0).id()).isEqualTo(ORDER_ID);
        assertThat(read.orders().get(0).students()).isEmpty();
    }

    @Test
    @Sql("/group/service/group3WithClient3AndOrders2AndStudents1.sql")
    void givenGroup3WithClient3AndOrders2AndStudents1_whenRead_thenReturn() {
        long GROUP_ID = 3L;
        long CLIENT_ID = 3L;
        long ORDER_ID = 2L;
        long STUDENT_ID = 1L;
        Group read = groupService.read(GROUP_ID);
        assertThat(read.id()).isEqualTo(GROUP_ID);
        assertThat(read.client().id()).isEqualTo(CLIENT_ID);
        assertThat(read.orders().get(0).id()).isEqualTo(ORDER_ID);
        assertThat(read.orders().get(0).students().get(0).id()).isEqualTo(STUDENT_ID);
    }

    @Test
    @Sql("/group/service/group4WithClient4AndOrders3And4AndStudents2And3And4And5.sql")
    void givenGroup4WithClient4AndOrders3And4AndStudents2And3And4And5_whenRead_thenReturn() {
        long GROUP_ID = 4L;
        long CLIENT_ID = 4L;
        List<Long> ORDER_IDS = List.of(3L, 4L);
        List<Long> STUDENT_IDS = List.of(2L, 3L, 4L, 5L);
        Group read = groupService.read(GROUP_ID);
        assertThat(read.id()).isEqualTo(GROUP_ID);
        assertThat(read.client().id()).isEqualTo(CLIENT_ID);
        assertThat(read.orders().stream().map(Order::id).toList())
            .containsAll(ORDER_IDS);
        assertThat(read.orders().stream()
                       .flatMap(o -> o.students().stream())
                       .map(Student::id)
                       .toList())
            .containsAll(STUDENT_IDS);
    }
}
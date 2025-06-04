package demo.infrastructure.api;

import demo.domain.model.DiplomaStudent;
import demo.domain.model.ElearningStudent;
import demo.domain.model.Group;
import demo.domain.model.Student;
import demo.domain.model.request.GroupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(
                scripts = "/infrastructure/api/before_GroupControllerIntegrationTest.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ),
        @Sql(
                scripts = "/infrastructure/api/after_GroupControllerIntegrationTest.sql",
                executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
})
class GroupControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static final String API_GROUPS = "/api/groups";
    private static final String GROUP_NUMBER = "001";
    private static final Long GROUP_ID = 4L;

    @Test
    void createGroup_integrationTest() {
        // Given
        GroupRequest request = GroupRequest.builder()
                .number(GROUP_NUMBER)
                .build();
        // When
        ResponseEntity<Group> response = restTemplate.postForEntity(
                API_GROUPS,
                request,
                Group.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        Group body = response.getBody();
        assertThat(body.getId()).isNotNull();
        assertThat(body.getNumber()).isEqualTo(GROUP_NUMBER);
    }

    @Test
    void givenGroup_WhenReadWithDefaultProjection_thenGroupWithOrdersAndStudents() {
        // Create group first
        ResponseEntity<Group> response = restTemplate.getForEntity(
                API_GROUPS + "/" + GROUP_ID + "?projection=default",
                Group.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Group body = response.getBody();
        assertThat(body.getId()).isNotNull();
        Student student = body.getOrders().get(0).getStudents().get(0);
        assertThat(student).isInstanceOf(Student.class);
    }

    @Test
    void givenGroup_WhenReadWithDiplomaProjection_thenGroupWithOrdersAndStudents() {
        // Create group first
        ResponseEntity<Group> response = restTemplate.getForEntity(
                API_GROUPS + "/" + GROUP_ID + "?projection=diploma",
                Group.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Group body = response.getBody();
        assertThat(body.getId()).isNotNull();
        Student student = body.getOrders().get(0).getStudents().get(0);
        assertThat(student).isInstanceOf(DiplomaStudent.class);
    }

    @Test
    void givenGroup_WhenReadWithElearningProjection_thenGroupWithOrdersAndStudents() {
        // Create group first
        ResponseEntity<Group> response = restTemplate.getForEntity(
                API_GROUPS + "/" + GROUP_ID + "?projection=elearning",
                Group.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Group body = response.getBody();
        assertThat(body.getId()).isNotNull();
        Student student = body.getOrders().get(0).getStudents().get(0);
        assertThat(student).isInstanceOf(ElearningStudent.class);
    }
}
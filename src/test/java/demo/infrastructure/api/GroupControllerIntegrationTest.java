package demo.infrastructure.api;

import demo.domain.model.Group;
import demo.domain.model.request.GroupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GroupControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static final String API_GROUPS = "/api/groups";

    private static final String GROUP_NUMBER = "001";

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
    void readGroup_withDifferentProjections_integrationTest() {
        // Create group first
        GroupRequest request = GroupRequest.builder()
                .number(GROUP_NUMBER)
                .build();
        ResponseEntity<Group> created = restTemplate.postForEntity(API_GROUPS, request, Group.class);
        // Read with different projections
        String[] projections = {"default", "diploma", "elearning"};

        for (String projection : projections) {
            ResponseEntity<Group> response = restTemplate.getForEntity(
                    API_GROUPS + "/" + created.getBody().getId() + "?projection=" + projection,
                    Group.class
            );

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }
}
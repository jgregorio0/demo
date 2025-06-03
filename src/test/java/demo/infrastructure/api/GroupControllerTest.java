package demo.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.domain.GroupService;
import demo.domain.model.Group;
import demo.domain.model.request.GroupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
@Import({GroupService.class}) // Assuming GroupService is a Spring Bean
class GroupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Long GROUP_ID = 1L;
    private static final Long CLIENT_ID = 2L;
    private static final String GROUP_NUMBER = "001";
    private static final String VALID_JSON = "{\"number\":\"001\"}";
    private static final String DEFAULT_PROJECTION = "default";

    @Test
    void createGroup_shouldReturnCreatedGroup() throws Exception {
        // Given
        GroupRequest request = GroupRequest.builder()
                .number(GROUP_NUMBER)
                .build();
        Group expectedResponse = Group.builder()
                .id(GROUP_ID)
                .number(GROUP_NUMBER)
                .build();

        // When
        when(groupService.create(any(GroupRequest.class)))
                .thenReturn(expectedResponse);
        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(GROUP_ID))
                .andExpect(jsonPath("$.number").value(GROUP_NUMBER));
    }

    @Test
    void readGroup_withDefaultProjection_shouldReturnGroup() throws Exception {
        // Given
        Group expectedResponse = Group.builder()
                .id(GROUP_ID)
                .number(GROUP_NUMBER)
                .build();
        // When
        when(groupService.read(GROUP_ID)).thenReturn(expectedResponse);
        mockMvc.perform(get("/api/groups/{id}", GROUP_ID)
                        .param("projection", DEFAULT_PROJECTION))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(GROUP_ID))
                .andExpect(jsonPath("$.number").value(GROUP_NUMBER));
    }

    @Test
    void readGroup_withUnsupportedProjection_shouldThrowError() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/groups/{id}", GROUP_ID)
                        .param("projection", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteGroup_shouldCallServiceDelete() throws Exception {
        // When
        mockMvc.perform(delete("/api/groups/{id}", GROUP_ID))
                .andExpect(status().isOk());
        // Then
        verify(groupService).delete(GROUP_ID);
    }
}
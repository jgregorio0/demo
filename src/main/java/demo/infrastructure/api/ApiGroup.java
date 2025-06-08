package demo.infrastructure.api;

import demo.domain.ApiGroupRepository;
import demo.domain.model.Group;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
public class ApiGroup implements ApiGroupRepository {

    private final RestTemplate restTemplate;

    @Override
    public List<Group> getGroups() {
        return restTemplate
                .exchange("/groups", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Group>>() {
        }).getBody();
    }
}
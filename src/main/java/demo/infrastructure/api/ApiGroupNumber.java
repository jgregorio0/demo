package demo.infrastructure.api;

import demo.api.ApiGroupNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class ApiGroupNumber implements ApiGroupNumberRepository {

    private final RestTemplate restTemplate;

    @Override
    public String getGroupNumber(Long id) {
        return restTemplate.getForObject(
                "/group-number/{id}",
                String.class,
                id
        );
    }

}
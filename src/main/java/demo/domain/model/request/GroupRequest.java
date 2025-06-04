package demo.domain.model.request;

import lombok.Builder;

import java.util.List;

@Builder
public record GroupRequest(
        Long id,
        String number,
        Long clientId,
        List<Long> orderIds) {
}

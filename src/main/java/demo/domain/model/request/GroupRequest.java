package demo.domain.model.request;

import lombok.Builder;

import java.util.List;

@Builder
public record GroupRequest(
        String number,
        Long clientId,
        List<Long> orderIds) {
}

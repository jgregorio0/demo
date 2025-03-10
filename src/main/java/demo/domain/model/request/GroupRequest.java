package demo.domain.model.request;

import java.util.List;


public record GroupRequest(
        String number,
        Long clientId,
        List<Long> orderIds) {
}

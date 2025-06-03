package demo.infrastructure.api.exception;


import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        LocalDateTime timestamp,
        String message,
        Integer statusCode) {
}
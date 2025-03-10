package demo.domain.model;

import lombok.Builder;

import java.util.List;
import java.util.Objects;

@Builder
public record Order(
        Long id,
        String name,
        Client billingClient,
        List<Student> students
) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

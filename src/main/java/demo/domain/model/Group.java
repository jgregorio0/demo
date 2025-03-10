package demo.domain.model;

import lombok.Builder;

import java.util.List;
import java.util.Objects;

@Builder
public record Group(
        Long id,
        String number,
        Client client,
        List<Order> orders) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

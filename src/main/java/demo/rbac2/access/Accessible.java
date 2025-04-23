package demo.rbac2.access;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public interface Accessible<T> {
    Map<Role, BiFunction<? super T, AccessLevel, Boolean>> getAccesses();

    default boolean hasAccess(Role role, T param, AccessLevel accessLevel) {
        return Optional.ofNullable(getAccesses().get(role))
                .map(f -> f.apply(param, accessLevel))
                .orElse(false);
    }


    default boolean hasAccess(User user, AccessLevel accessLevel, T param) {
        return user.roles().stream()
                .anyMatch(r -> this.hasAccess(r, param, accessLevel));
    }
}

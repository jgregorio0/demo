package demo.rbac2.access;

import java.util.Set;

public record User(String nombre, Set<Role> roles) {
}

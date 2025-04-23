package demo.rbac1.access;

import java.util.List;

public record User(String nombre, List<Role> roles) {
}

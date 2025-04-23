package demo.rbac1;

import demo.rbac1.access.AccessLevel;
import demo.rbac1.access.Role;
import demo.rbac1.access.User;
import demo.rbac1.course.Course;
import demo.rbac1.course.CourseType;
import demo.rbac1.course.RoleCourseTypePermissions;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        User user = new User("Administrator", List.of(Role.ADMIN));
        Course course = new Course("Open Course", CourseType.OPEN);
        Set<AccessLevel> permissions = user.roles().stream()
                .flatMap(r -> new RoleCourseTypePermissions()
                        .getAccess(r, course.type()).stream())
                .collect(Collectors.toSet());
        boolean hasReadPermission = user.roles().stream()
                .anyMatch(r -> new RoleCourseTypePermissions()
                        .hasAccess(r, course.type(), AccessLevel.READ));
        System.out.println("user: " + user +
                " has permissions: " + permissions +
                " and has read permision: " + hasReadPermission);
    }
}

package demo.rbac1.course;

import demo.rbac1.access.Accessible;
import demo.rbac1.access.AccessLevel;
import demo.rbac1.access.Role;

import java.util.Set;

public class EraserCoursePermissions implements Accessible {

    @Override
    public Set<AccessLevel> getAccess(Role role, CourseType courseType) {
        return Set.of(AccessLevel.READ, AccessLevel.DELETE);
    }

    @Override
    public boolean hasAccess(Role role, CourseType courseType, AccessLevel permission) {
        return AccessLevel.READ.equals(permission) ||
                AccessLevel.DELETE.equals(permission);
    }
}

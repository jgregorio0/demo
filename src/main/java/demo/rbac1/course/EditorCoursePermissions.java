package demo.rbac1.course;

import demo.rbac1.access.Accessible;
import demo.rbac1.access.AccessLevel;
import demo.rbac1.access.Role;

import java.util.Set;

public class EditorCoursePermissions implements Accessible {

    @Override
    public Set<AccessLevel> getAccess(Role role, CourseType courseType) {
        if (CourseType.OPEN.equals(courseType)) {
            return Set.of(AccessLevel.CREATE, AccessLevel.READ);
        } else {
            return Set.of(AccessLevel.READ);
        }
    }

    @Override
    public boolean hasAccess(Role role, CourseType courseType, AccessLevel permission) {
        return (AccessLevel.CREATE.equals(permission) &&
                CourseType.OPEN.equals(courseType)) ||
                AccessLevel.READ.equals(permission) ||
                AccessLevel.UPDATE.equals(permission);
    }
}

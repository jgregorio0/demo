package demo.rbac1.access;

import demo.rbac1.course.CourseType;

import java.util.Set;

public interface Accessible {
    Set<AccessLevel> getAccess(Role role, CourseType courseType);

    boolean hasAccess(Role role, CourseType courseType, AccessLevel permission);
}

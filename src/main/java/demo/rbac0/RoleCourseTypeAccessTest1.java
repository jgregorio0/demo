package demo.rbac0;

import demo.rbac1.access.AccessLevel;
import demo.rbac1.access.Role;
import demo.rbac1.access.User;
import demo.rbac1.course.Course;
import demo.rbac1.course.CourseType;

import java.util.List;

public class RoleCourseTypeAccessTest1 {
    public static void main(String[] args) {
        User admin = new User("Administrator", List.of(Role.ADMIN));
        Course openCourse = new Course("Open Course", CourseType.OPEN);
        boolean hasReadPermission = admin.roles().stream()
                .anyMatch(r -> hasAccess(r, AccessLevel.READ, openCourse.type()));
    }

    private static boolean hasAccess(Role role, AccessLevel accessLevel, CourseType courseType) {
        if (Role.ADMIN.equals(role)) {
            // has full access
            return true;
        } else if (Role.AGENT.equals(role)) {
            // has no create permission
            // has read for all courses
            if (AccessLevel.READ.equals(accessLevel)) {
                return true;
            }
        } else if (Role.EDITOR.equals(role)) {
            // has create for Open course only
            if (AccessLevel.CREATE.equals(accessLevel)){
                if (CourseType.OPEN.equals(courseType)) {
                    return true;
                }
            } else if (AccessLevel.UPDATE.equals(accessLevel)) {
                return true;
            } else if (AccessLevel.READ.equals(accessLevel)) {
                return true;
            }
        } else if (Role.ERASER.equals(role)) {
            if(AccessLevel.READ.equals(accessLevel)) {
                return true;
            } else if (AccessLevel.DELETE.equals(accessLevel)) {
                return true;
            }
        }
        return false;
    }
}

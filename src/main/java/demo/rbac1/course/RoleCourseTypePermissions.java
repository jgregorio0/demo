package demo.rbac1.course;

import demo.rbac1.access.Accessible;
import demo.rbac1.access.AccessLevel;
import demo.rbac1.access.Role;

import java.util.Map;
import java.util.Set;

public class RoleCourseTypePermissions implements Accessible {
    private final static Map<Role, Accessible> strategies = Map.ofEntries(
            Map.entry(Role.ADMIN, new AdministratorCourseTypePermissions()),
            Map.entry(Role.AGENT, new AgentCourseTypePermissions()),
            Map.entry(Role.EDITOR, new EditorCoursePermissions()),
            Map.entry(Role.ERASER, new EraserCoursePermissions())
    );

    @Override
    public Set<AccessLevel> getAccess(Role role, CourseType courseType) {
        return strategies.get(role).getAccess(role, courseType);
    }

    @Override
    public boolean hasAccess(Role role, CourseType courseType, AccessLevel permission) {
        return strategies.get(role).hasAccess(role, courseType, permission);
    }
}

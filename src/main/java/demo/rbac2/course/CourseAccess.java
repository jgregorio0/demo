package demo.rbac2.course;

import demo.rbac2.access.AccessLevel;
import demo.rbac2.access.Accessible;
import demo.rbac2.access.Role;

import java.util.Map;
import java.util.function.BiFunction;

import static demo.rbac2.course.CourseAccessFunction.readAndWriteByCourse;
import static demo.rbac2.access.ObjectAccess.*;


public class CourseAccess implements Accessible<Course> {
    private final Map<Role, BiFunction<? super Course, AccessLevel, Boolean>> accesses;

    public CourseAccess() { // TODO use singleton
        accesses =
                Map.ofEntries(
                        Map.entry(Role.ADMIN, fullAccess),
                        Map.entry(Role.AGENT, readOnly),
                        Map.entry(Role.EDITOR, readAndWriteByCourse),
                        Map.entry(Role.ERASER, readAndDelete)
                );
    }

    @Override
    public Map<Role, BiFunction<? super Course, AccessLevel, Boolean>> getAccesses() {
        return accesses;
    }
}

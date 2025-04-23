package demo.rbac2;

import demo.rbac2.access.AccessLevel;
import demo.rbac2.access.Role;
import demo.rbac2.access.User;
import demo.rbac2.course.Course;
import demo.rbac2.course.CourseAccess;
import demo.rbac2.course.CourseType;
import demo.rbac2.helpfile.HelpFile;
import demo.rbac2.helpfile.HelpFileAccess;
import demo.rbac2.helpfile.HelpFileAccessParam;

import java.util.Set;

public class Test {
    public static void main(String[] args) {
        User editor = new User("Editor", Set.of(Role.EDITOR));
        Course openCourse = new Course("Open Course", CourseType.OPEN);
        Course closedCourse = new Course("Open Course", CourseType.CLOSE);
        HelpFile activeHelpFile = new HelpFile("Active help file", true);
        HelpFile inactiveHelpFile = new HelpFile("Active help file", false);
        CourseAccess courseAccess = new CourseAccess();
        boolean shouldHasAccessToUpdateCourse = courseAccess.hasAccess(editor, AccessLevel.UPDATE, openCourse);
        System.out.println("shouldHasAccessToUpdateCourse " + shouldHasAccessToUpdateCourse);
        boolean shouldNotHasAccessToUpdateCourse = courseAccess.hasAccess(editor, AccessLevel.UPDATE, closedCourse);
        System.out.println("shouldNotHasAccessToUpdateCourse " + shouldNotHasAccessToUpdateCourse);
        HelpFileAccess helpFileAccess = new HelpFileAccess();
        boolean shouldHasAccessToUpdateHelpFile =
                helpFileAccess.hasAccess(editor, AccessLevel.UPDATE, new HelpFileAccessParam(activeHelpFile, openCourse));
        System.out.println("shouldHasAccessToUpdateHelpFile " + shouldHasAccessToUpdateHelpFile);
        boolean shouldNotHasAccessToUpdateHelpFile =
                helpFileAccess.hasAccess(editor, AccessLevel.UPDATE, new HelpFileAccessParam(inactiveHelpFile, openCourse));
        System.out.println("shouldNotHasAccessToUpdateHelpFile " + shouldNotHasAccessToUpdateHelpFile);
    }
}

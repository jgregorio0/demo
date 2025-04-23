package demo.rbac2.helpfile;

import demo.rbac2.access.AccessLevel;
import demo.rbac2.course.CourseType;

import java.util.function.BiFunction;


public class HelpFileAccessFunction {
    protected static final BiFunction<HelpFileAccessParam, AccessLevel, Boolean> readAndWriteByCourseOpenAndHelpFileActive =
            (param, level) -> switch (level) {
                case READ -> true;
                case UPDATE -> CourseType.OPEN.equals(param.course().type()) &&
                        param.helpFile().active();
                default -> false;
            };
}

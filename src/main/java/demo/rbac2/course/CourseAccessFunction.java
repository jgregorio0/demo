package demo.rbac2.course;

import demo.rbac2.access.AccessLevel;

import java.util.function.BiFunction;


public class CourseAccessFunction {
    protected static final BiFunction<Course, AccessLevel, Boolean> readAndWriteByCourse = (course, level) ->
            switch (level) {
                case READ -> true;
                case UPDATE -> CourseType.OPEN.equals(course.type());
                default -> false;
            };
}

package demo.rbac2.access;

import java.util.function.BiFunction;


public class ObjectAccess {
    public static final BiFunction<Object, AccessLevel, Boolean> fullAccess =
            (course, level) -> true;
    public static final BiFunction<Object, AccessLevel, Boolean> readOnly =
            (course, level) -> AccessLevel.READ.equals(level);
    public static final BiFunction<Object, AccessLevel, Boolean> readAndDelete = (course, level) ->
            switch (level) {
                case READ, DELETE -> true;
                default -> false;
            };
}

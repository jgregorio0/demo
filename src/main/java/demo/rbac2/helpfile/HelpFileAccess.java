package demo.rbac2.helpfile;

import demo.rbac2.access.AccessLevel;
import demo.rbac2.access.Accessible;
import demo.rbac2.access.Role;

import java.util.Map;
import java.util.function.BiFunction;

import static demo.rbac2.helpfile.HelpFileAccessFunction.readAndWriteByCourseOpenAndHelpFileActive;
import static demo.rbac2.access.ObjectAccess.*;


public class HelpFileAccess implements Accessible<HelpFileAccessParam> {

    private final Map<Role, BiFunction<? super HelpFileAccessParam, AccessLevel, Boolean>> accesses;

    public HelpFileAccess() {
        accesses = Map.ofEntries(
                Map.entry(Role.ADMIN, fullAccess),
                Map.entry(Role.AGENT, readOnly),
                Map.entry(Role.EDITOR, readAndWriteByCourseOpenAndHelpFileActive),
                Map.entry(Role.ERASER, readAndDelete)
        );
    }

    @Override
    public Map<Role, BiFunction<? super HelpFileAccessParam, AccessLevel, Boolean>> getAccesses() {
        return accesses;
    }
}

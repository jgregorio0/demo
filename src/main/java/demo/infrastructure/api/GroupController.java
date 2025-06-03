package demo.infrastructure.api;

import demo.domain.exception.DomainException;
import demo.domain.model.Group;
import demo.domain.GroupService;
import demo.domain.model.request.GroupRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    private static final String DEFAULT_PROJECTION = "default";
    private static final String DIPLOMA_PROJECTION = "diploma";
    private static final String ELEARNING_PROJECTION = "elearning";

    @PostMapping
    public ResponseEntity<Group> create(
            @RequestBody GroupRequest input) {
        return ResponseEntity.ok(groupService.create(input));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> read(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = DEFAULT_PROJECTION) final String projection) {
        return switch (projection) {// TODO JG strategy pattern
            case DEFAULT_PROJECTION -> ResponseEntity.ok(groupService.read(id));
            case DIPLOMA_PROJECTION -> ResponseEntity.ok(groupService.readWithOrdersAndDiplomaStudents(id));
            case ELEARNING_PROJECTION -> ResponseEntity.ok(groupService.readWithOrdersAndElearningStudents(id));
            default -> throw new DomainException("UNSUPPORTED_PROJECTION");
        };
    }

    /* TODO @PutMapping("/{id}")
    public ResponseEntity<Group> update(
            @PathVariable Long id,
            @RequestBody GroupRequest input) {
        return ResponseEntity.ok(groupService.update(id, input));
    }*/

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }
}

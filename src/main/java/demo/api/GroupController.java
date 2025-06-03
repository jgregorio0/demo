package demo.api;

import demo.domain.GroupService;
import demo.domain.model.Group;
import demo.domain.model.request.GroupRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;


    @GetMapping
    public String test2() {
        return "hola2";
    }

    @PostMapping
    public ResponseEntity<Group> create(
            @RequestBody GroupRequest input) {
        return ResponseEntity.ok(Group.builder().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> read(
            @PathVariable Long id) {
        return ResponseEntity.ok(groupService.read(id));
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

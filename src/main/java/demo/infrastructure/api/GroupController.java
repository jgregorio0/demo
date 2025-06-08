package demo.infrastructure.api;

import demo.domain.GroupService;
import demo.domain.model.Group;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Group>> getGroups() {
        return ResponseEntity.ok(groupService.getGroupsWithNumbers());
    }
}

package vacationproject.lobster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.dto.AddGroupRequest;
import vacationproject.lobster.dto.GroupResponse;
import vacationproject.lobster.dto.UpdateCalenderRequest;
import vacationproject.lobster.dto.UpdateGroupRequest;
import vacationproject.lobster.service.GroupService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GroupController {

    private final GroupService groupService;

    //Group 생성
    @PostMapping("/api/groups")
    public ResponseEntity<Group> createGroup(@RequestBody AddGroupRequest request) {
        Group savedGroup = groupService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedGroup);
    }

    //Group 목록 조회
    @GetMapping("/api/groups")
    public ResponseEntity<List<GroupResponse>> findAllGroups() {
        List<GroupResponse> groups = groupService.findAll()
                .stream()
                .map(GroupResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(groups);
    }

    //Group id로 단건 조회
    @GetMapping("/api/groups/{id}")
    public ResponseEntity<GroupResponse> findGroupById(@PathVariable long id) {
        Group group = groupService.findById(id);

        return ResponseEntity.ok()
                .body(new GroupResponse(group));
    }

    //Group 삭제
    @DeleteMapping("/api//groups/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable long id) {
        groupService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    //Group 수정
    @PutMapping("/api/groups/{id}")
    public ResponseEntity<Group> updateArticle(@PathVariable long id,
                                                  @RequestBody UpdateGroupRequest request) {
        Group updatedGroup = groupService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedGroup);
    }
}

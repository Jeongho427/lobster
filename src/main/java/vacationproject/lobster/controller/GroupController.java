package vacationproject.lobster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.dto.AddGroupRequest;
import vacationproject.lobster.dto.GroupResponse;
import vacationproject.lobster.dto.UpdateGroupRequest;
import vacationproject.lobster.service.GroupService;

import java.nio.file.AccessDeniedException;
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
    @DeleteMapping("/api/groups/{id}/delete")
    public ResponseEntity<Void> deleteGroup(@PathVariable long id) {
        groupService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    //Group 탈퇴
    @PostMapping("/api/groups/{id}/leave")
    public ResponseEntity<Void> leaveGroup(@PathVariable long groupId){
        groupService.leaveGroup(groupId);

        return ResponseEntity.ok()
                .build();
    }

    

    // 그룹에서 아이디 검색 후 이메일 보내기
    @PostMapping("/{groupId}/invite")
    public ResponseEntity<String> inviteUserToGroup(@PathVariable("groupId") String groupId, @RequestParam("userId") String userId) {
        try {
            groupService.inviteUserToGroup(groupId, userId);
            return ResponseEntity.ok("User invited to group successfully!!!");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only group creator can invite members.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid group ID.");
        }
    }

    // 그룹 수정
    @PutMapping("/api/groups/{id}")
    public ResponseEntity<Group> updateArticle(@PathVariable long id,
                                                  @RequestBody UpdateGroupRequest request) {
        Group updatedGroup = groupService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedGroup);
    }
}

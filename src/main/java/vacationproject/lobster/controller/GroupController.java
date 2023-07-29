package vacationproject.lobster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.AddGroupRequest;
import vacationproject.lobster.dto.GroupResponse;
import vacationproject.lobster.dto.UpdateGroupRequest;
import vacationproject.lobster.repository.UserRepository;
import vacationproject.lobster.service.GroupService;
import vacationproject.lobster.service.InvitationService;
import vacationproject.lobster.service.MailSenderService;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {

    private final GroupService groupService;
    private final UserRepository userRepository;
    private final InvitationService invitationService;
    private final MailSenderService mailSenderService;

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
    @DeleteMapping("/api/groups/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable long id) {
        groupService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    // 그룹 초대 기능
    @PostMapping("/api/invite/{groupId}") // 그룹의 아이디와 사용자 로그인 아이디를 받아옴.
    public String inviteUser(@PathVariable long groupId, @RequestBody Map<String, String> request) {
        String userId = request.get("userId");

        User user = userRepository.findByUserId(userId);

        if (user != null && user.getEmail() != null) {
            String invitationToken = invitationService.generateInvitationToken(groupId, userId);
            // 이것이 링크
            String invitationLink = "http://localhost:8080/api/" + groupId + "/invite" + invitationToken + "_" + userId;
            // mail 보내기 코드
            mailSenderService.invite(user.getEmail(), invitationLink);

            return invitationLink;
        } else {
            return "User not found or already exits.";
        }
    }

    // 그룹에 링크를 누르면 멤버를 추가하는 기능
    @GetMapping("/api/{groupId}／invite_/{token}/_{userId}")
    public Map<String, String> joinGroup(
            @PathVariable long groupId,
            @RequestParam("token") String token,
            @RequestParam("userId") String loginId
    ) { String userIdAndGroupId = invitationService.getUserIdAndGroupIdFromToken(token);

        if (userIdAndGroupId != null) {
            String[] userIdAndGroupIdArray = userIdAndGroupId.split("_");
            String userId = userIdAndGroupIdArray[0]; // 토큰에서 해체한 유저 아이디
            long invitedGroupId = Long.parseLong(userIdAndGroupIdArray[1]); // 토큰해서 해제한 그룹 아이디.

            // 초대 링크의 그룹 ID와 요청한 그룹 ID가 일치하는지 확인
            if (groupId == invitedGroupId) {

                // 현재 로그인한 사용자의 아이디와 초대된 유저의 아이디를 비교
                if (userId.equals( loginId)) {
                    // 일치하면 그룹에 멤버를 추가
                    groupService.addMemberToGroup(groupId, userId);

                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Successfully joined the group.");
                    return response;
                } else {
                    Map<String, String> response = new HashMap<>();
                    response.put("error", "You are not authorized to join this group.");
                    return response;
                }
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid or expired invitation link.");
        return response;
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

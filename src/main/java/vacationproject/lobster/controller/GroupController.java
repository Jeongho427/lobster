package vacationproject.lobster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vacationproject.lobster.Security.JwtProvider;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.group.AddGroupRequest;
import vacationproject.lobster.dto.group.CombinedCalendarResponse;
import vacationproject.lobster.dto.group.GroupResponse;
import vacationproject.lobster.dto.group.UpdateGroupRequest;
import vacationproject.lobster.repository.UserRepository;
import vacationproject.lobster.service.GroupService;
import vacationproject.lobster.service.InvitationService;
import vacationproject.lobster.service.MailSenderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {

    private final GroupService groupService;
    private final UserRepository userRepository;
    private final InvitationService invitationService;
    private final MailSenderService mailSenderService;
    private final JwtProvider jwtProvider;

    //Group 생성
    @PostMapping("/api/groups/{userId}")
    public ResponseEntity<Group> createGroup(@PathVariable long userId,
                                             @RequestHeader HttpHeaders headers,
                                             @RequestBody AddGroupRequest request) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtProvider.extractUIdFromToken(token);
        Group savedGroup = groupService.save(request, uId);

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
    @GetMapping("/api/groups/{groupId}")
    public ResponseEntity<GroupResponse> findGroupById(@RequestHeader HttpHeaders auth,
                                                       @PathVariable long groupId) {
        Group group = groupService.findById(groupId);

        return ResponseEntity.ok()
                .body(new GroupResponse(group));
    }

    //Group 삭제
    @DeleteMapping("/api/groups/{groupId}")
    public ResponseEntity<Void> deleteGroup(@RequestHeader HttpHeaders auth,
                                            @PathVariable long groupId) {
        groupService.delete(groupId);

        return ResponseEntity.ok()
                .build();
    }

    //Group 탈퇴
    @DeleteMapping("/api/groups/{groupId}/leave")
    public ResponseEntity<Void> leaveGroup(@RequestHeader HttpHeaders auth,
                                           @PathVariable long groupId,
                                           @RequestParam long userId) {
        groupService.leaveGroup(groupId, userId);
        return ResponseEntity.ok().build();
    }

    //그룹원 일정 다 모인 캘린더 보기
    @GetMapping("/api/groups/{userId}/combined-calendar")
    public ResponseEntity<CombinedCalendarResponse> getCombinedCalendarForGroup(@RequestHeader HttpHeaders auth,
                                                                                @PathVariable long userId) {
        CombinedCalendarResponse combinedCalendar = groupService.getCombinedCalendarForGroup(userId);

        return ResponseEntity.ok(combinedCalendar);
    }

    // 그룹 초대 기능
    @PostMapping("/api/invite/{groupId}") // 그룹의 아이디와 사용자 로그인 아이디를 받아옴.
    public String inviteUser(@RequestHeader HttpHeaders auth,
                             @PathVariable long groupId,
                             @RequestBody Map<String, String> request) {
        String userId = request.get("userId");

        User user = userRepository.findByUserId(userId);

        if (user != null && user.getEmail() != null) {
            String invitationToken = invitationService.generateInvitationToken(groupId, userId);
            // 이것이 링크
            String invitationLink = "http://localhost:8080/api/" + groupId + "/invite" + invitationToken + "_" + userId;
            // mail 보내기 코드
            mailSenderService.invite(user.getEmail(), invitationLink);

            return invitationToken;
        } else {
            return "User not found or already exits.";
        }
    }

    // 그룹에 링크를 누르면 멤버를 추가하는 기능
    @GetMapping("/api/inviteToken")
    public Map<String, String> joinGroup(
            @RequestParam("groupId") long groupId,
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
    public ResponseEntity<Group> updateArticle(@RequestHeader HttpHeaders auth,
                                               @PathVariable long id,
                                               @RequestBody UpdateGroupRequest request) {
        Group updatedGroup = groupService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedGroup);
    }
}
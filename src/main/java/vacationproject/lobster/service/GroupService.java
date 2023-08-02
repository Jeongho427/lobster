package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vacationproject.lobster.domain.Calendar;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.Member;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.AddGroupRequest;
import vacationproject.lobster.dto.CombinedCalendarResponse;
import vacationproject.lobster.dto.UpdateGroupRequest;
import vacationproject.lobster.repository.GroupRepository;
import vacationproject.lobster.repository.MemberRepository;
import vacationproject.lobster.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;


    //Group 등록
    public Group save(AddGroupRequest groupRequest) {
        // memberCnt를 1로 설정
        groupRequest.setMemberCnt(1);

        // creator 객체에서 user_id를 가져와 Group 엔티티에 설정
        User creator = groupRequest.getCreator();
        if (creator != null) {
            String userId = creator.getUserId();
            // userId가 null이거나 비어있지 않다고 가정하고, Group 엔티티에 creator를 설정
            groupRequest.setCreator(User.builder().userId(userId).build());
        } else {
            // creator가 null인 경우에 대한 처리를 합니다. (필요하면 추가)
            // creator가 null일 수 없다고 가정
            throw new IllegalArgumentException("Creator cannot be null.");
        }

        return groupRepository.save(groupRequest.toEntity());
    }

    //Group 전체 조회
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    //Group id로 단건 조회
    public Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    //Group 삭제
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    //Group 탈퇴
    public void leaveGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group ID."));

        User creator = group.getCreator();
        Long userId = creator.getUId();

        // 해당 그룹에 속한 모든 멤버 조회
        List<Member> members = group.getMembers();

        // 그룹 생성자인 경우 그룹 삭제 및 멤버들 삭제
        if (group.getCreator().getUserId().equals(userId)) {
            for (Member member : members) {
                memberRepository.deleteById(member.getMId());
                groupRepository.decrementMemberCount(groupId);
            }
            groupRepository.deleteById(groupId);
        }
        else {
            // 그룹에서 해당 사용자 삭제
            for (Member member : members) { // Member 엔티티의 userId 필드에 있는 User 엔티티의 userId 필드를 비교
                if (member.getGroupId().getGId().equals(groupId) && member.getUserId().getUId().equals(userId)) {
                    memberRepository.deleteById(member.getMId());
                    // Group 엔티티의 멤버 수 감소 메서드 호출
                    groupRepository.decrementMemberCount(groupId);
                    break;
                }
            }
        }
    }

    //Group 수정 (이름만 수정 가능) 멤버 수는 자동으로 갱신되게, 그룹 생성자는 안바뀌게
    public Group update(Long id, UpdateGroupRequest request) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        return group;
    }

    //그룹원들 달력 한 달력에 모아주기
    public CombinedCalendarResponse getCombinedCalendarForGroup(long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + groupId));

        // 그룹원들의 캘린더 일정 리스트를 가져옴
        List<Calendar> combinedCalendars = new ArrayList<>();
        for (Member member : group.getMembers()) {
            combinedCalendars.add(member.getUserId().getCalendar());
        }

        return new CombinedCalendarResponse(combinedCalendars);
    }

    // 해당 그룹에 멤버 추가 기능
    public void addMemberToGroup(long groupId, String userId) {
        User user = userRepository.findByUserId(userId);

        // 그룹이 데이터베이스에 있는지 확인하고 있으면 memberCnt를 1 증가시킵니다.
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));

        groupRepository.incrementMemberCount(groupId);

        // 이미 해당 유저가 멤버인지 확인 (중복 가입 방지)
        boolean isUserAlreadyMember = memberRepository.existsByGroupIdAndUserId(group, user);

        if (isUserAlreadyMember) {
            throw new IllegalArgumentException("User with id " + userId + " is already a member of the group.");
        }

        Member member = Member.builder()
                .groupId(group)
                .userId(user)
                .color("some_color") // 멤버의 컬러 정보를 추가로 설정하려면 이 부분을 수정
                .build();

        memberRepository.save(member);

    }
}
package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.Member;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.AddGroupRequest;
import vacationproject.lobster.dto.UpdateGroupRequest;
import vacationproject.lobster.repository.GroupRepository;
import vacationproject.lobster.repository.MemberRepository;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    //Group 생성
    public Group save(AddGroupRequest groupRequest) {
        // memberCnt를 1로 설정
        groupRequest.setMemberCnt(1);

        // creator 객체에서 user_id를 가져와 Group 엔티티에 설정
        User creator = groupRequest.getCreator();
        if (creator != null) {
            String userId = creator.getUserId();
            // userId가 null이거나 비어있지 않다고 가정하고, Group 엔티티에 creator를 설정
            groupRequest.setCreator(User.builder().userId(userId).build());
        }
        else {
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
    public void leaveGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group ID."));

        // 해당 그룹에 속한 모든 멤버 조회
        List<Member> members = group.getMembers();

        // 그룹 생성자인 경우 그룹 삭제 및 멤버들 삭제
        if (group.getCreator().getUserId().equals(userId)) {
            for (Member member : members) {
                memberRepository.deleteById(member.getMId());
            }
            groupRepository.deleteById(groupId);
        }
        else {
            // 그룹에서 해당 사용자 삭제
            for (Member member : members) { // Member 엔티티의 userId 필드에 있는 User 엔티티의 userId 필드를 비교
                if (member.getGroupId().equals(groupId) && member.getUserId().equals(userId)) {
                    memberRepository.deleteById(member.getMId());
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

    //그룹 초대
    public void inviteUserToGroup(String groupId, String userId) throws AccessDeniedException {
        String currentUserId = "creator_id"; // 현재 로그인한 사용자의 u_id

        if (!currentUserId.equals("creator_id")) {
            throw new AccessDeniedException("Only group creator can invite members.");
        }
    }

    //Group에서 초대 권한이 생성자에게 있기 때문에 생성자가 나가면 그룹에 새로운 인원 초대가 안되므로 생성자를 갱신해줘야함
    //=> member에서 할까?


}

package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.AddGroupRequest;
import vacationproject.lobster.dto.UpdateGroupRequest;
import vacationproject.lobster.repository.GroupRepository;
import org.springframework.stereotype.Service;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.Member;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.repository.GroupRepository;
import vacationproject.lobster.service.UserService;
import java.util.Optional;

//추가
import org.springframework.beans.factory.annotation.Autowired;


import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;

    //Group 등록
    public Group save(AddGroupRequest groupRequest) {
        return groupRepository.save(groupRequest.toEntity());
    }

    //Group 전체 조회
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    //Group id로 조회
    public Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    //Group 삭제
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    //Group 수정 (이름만 수정 가능) <= 아직 다 작성하지 않은 것
    @Transactional
    public Group update(Long id, UpdateGroupRequest updateGroupRequest) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        return group;
    }

    public void inviteUserToGroup(String groupId, String userId) throws AccessDeniedException {
        String currentUserId = "creator_id"; // 현재 로그인한 사용자의 u_id

        if (!currentUserId.equals("creator_id")) {
            throw new AccessDeniedException("Only group creator can invite members.");
        }



//        // 그룹에 멤버로 추가하고 저장
//        Group group = groupRepository.findById(Long.parseLong(groupId)).orElseThrow(() -> new IllegalArgumentException("Invalid group ID."));
//        Member member = new Member(group, user, "default_color"); // 여기서 "default_color"는 예시로 사용자의 색상 정보를 나타냅니다.
//        group.getMembers().add(member);
//        groupRepository.save(group);
    }





}

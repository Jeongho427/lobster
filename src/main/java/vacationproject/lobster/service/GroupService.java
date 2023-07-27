package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.dto.AddGroupRequest;
import vacationproject.lobster.dto.UpdateGroupRequest;
import vacationproject.lobster.repository.GroupRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupService {

    private final GroupRepository groupRepository;

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

    //Group 수정 (이름만 수정 가능) 멤버 수는 자동으로 갱신되게, 그룹 생성자는 안바뀌게
    @Transactional
    public Group update(Long id, UpdateGroupRequest request) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        group.update(request.getGroupName());

        return group;
    }

    //Group에서 초대 권한이 생성자에게 있기 때문에 생성자가 나가면 그룹에 새로운 인원 초대가 안되므로 생성자를 갱신해줘야함
    //=> member에서 할까?


}

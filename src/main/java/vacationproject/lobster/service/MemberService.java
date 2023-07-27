package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vacationproject.lobster.domain.Member;
import vacationproject.lobster.dto.AddMemberRequest;
import vacationproject.lobster.dto.UpdateMemberRequest;
import vacationproject.lobster.repository.MemberRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    //Member 생성
    public Member save(AddMemberRequest memberRequest) {
        return memberRepository.save(memberRequest.toEntity());
    }

    //Member 전체 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    //Member id로 조회
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    //Member 삭제
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    //Member 수정
    @Transactional
    public Member update(Long id, UpdateMemberRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        member.update(request.getGroupId(), request.getUserId(), request.getColor());

        //member에서 탈퇴한 group원이 creator인 경우 creator의 권한을 다른 사람에게 넘겨줘야한다

        return member;
    }


}

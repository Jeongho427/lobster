package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vacationproject.lobster.domain.Member;
import vacationproject.lobster.dto.AddMemberRequest;
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

}

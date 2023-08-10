package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vacationproject.lobster.Security.JwtProvider;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.calender.AddCalenderRequest;
import vacationproject.lobster.dto.calender.UpdateCalenderRequest;
import vacationproject.lobster.repository.CalenderRepository;
import vacationproject.lobster.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CalenderService {

    private final CalenderRepository calenderRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    // 일정 생성
    public Calender save(AddCalenderRequest calenderRequest, Long uId) {
        User user = userRepository.findById(uId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        Calender newCalender = calenderRequest.toEntity(user);
        return calenderRepository.save(newCalender);
    }

    // CalenderOwner id로 해당 사용자의 일정들 가져오기
    public List<Calender> findUserCalenders(Long userId) {
        return calenderRepository.findByCalenderOwnerId(userId);
    }

    // 일정 조회
    public Calender findById(Long id) {
        return calenderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 일정 삭제
    public void delete(Long id) {
        calenderRepository.deleteById(id);
    }

    // 일정 수정
    public Calender update(Long calenderId, UpdateCalenderRequest calenderRequest) {
        Calender existingCalender = calenderRepository.findById(calenderId)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다"));

        // 기존 일정의 정보를 가져와서 업데이트합니다.
        existingCalender.update(calenderRequest.getDay_start(), calenderRequest.getDay_end(), calenderRequest.getContents());
        return calenderRepository.save(existingCalender);
    }
}

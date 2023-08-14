package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vacationproject.lobster.Security.JwtProvider;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.calender.AddCalenderRequest;
import vacationproject.lobster.dto.calender.CalenderResponse;
import vacationproject.lobster.dto.calender.UpdateCalenderRequest;
import vacationproject.lobster.repository.CalenderRepository;
import vacationproject.lobster.repository.UserRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
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
    public List<Calender> findUserCalenders(Long uId) {
        return calenderRepository.findByCalenderOwnerId(uId);
    }

    // 특정 달 및 앞뒤 3달치 일정 가져오기
    public List<CalenderResponse> getCalendersForMonth(Long userId, int year, int month) {
        List<Calender> calenders = calenderRepository.findByCalenderOwnerId(userId);

        YearMonth targetMonth = YearMonth.of(year, month);

        LocalDate startDate = targetMonth.atDay(1).minusMonths(1); // 앞 달의 시작일
        LocalDate endDate = targetMonth.atEndOfMonth().plusMonths(1); // 뒷 달의 마지막일

        List<CalenderResponse> calendersForMonth = new ArrayList<>();
        for (Calender calender : calenders) {
            LocalDate calenderDate = calender.getDay_start().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (!calenderDate.isBefore(startDate) && !calenderDate.isAfter(endDate)) {
                calendersForMonth.add(new CalenderResponse(calender));
            }
        }

        return calendersForMonth;
    }


    // 일정 조회
    public Calender findById(Long calenderId) {
        return calenderRepository.findById(calenderId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + calenderId));
    }

    // 일정 삭제
    public void delete(Long calenderId) {
        calenderRepository.deleteById(calenderId);
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

package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.dto.calender.AddCalenderRequest;
import vacationproject.lobster.dto.calender.UpdateCalenderRequest;
import vacationproject.lobster.repository.CalenderRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CalenderService {

    private final CalenderRepository calenderRepository;

    //Calender 생성
    public Calender save(AddCalenderRequest calenderRequest) {
        return calenderRepository.save(calenderRequest.toEntity());
    }

    //Calender 전체 조회
    public List<Calender> findAll() {
        return calenderRepository.findAll();
    }

    //Calender id로 조회
    public Calender findById(Long id) {
        return calenderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    //Calender 삭제
    public void delete(Long id) {
        calenderRepository.deleteById(id);
    }

    // Calender 수정 => 일정 추가 및 수정
    public Calender update(Long id, UpdateCalenderRequest request) {
        Calender calender = calenderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        calender.update(request.getDayStart(), request.getDayEnd(), request.getTimeStart(), request.getTimeEnd(),
                request.getContents());

        return calender;
    }
}

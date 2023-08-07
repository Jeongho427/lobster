package vacationproject.lobster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.calender.AddCalenderRequest;
import vacationproject.lobster.dto.calender.CalenderResponse;
import vacationproject.lobster.dto.calender.UpdateCalenderRequest;
import vacationproject.lobster.repository.UserRepository;
import vacationproject.lobster.service.CalenderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CalenderController {

    private final CalenderService calenderService;
    private final UserRepository userRepository;

    //Calender 생성
    @PostMapping("/api/calenders")
    public ResponseEntity<Calender> createCalender(@RequestBody AddCalenderRequest request) {
        // 캘린더 생성 예시 데이터의 User 부분 (calenderOwner)을 먼저 저장
        User user = userRepository.save(request.getCalenderOwner());

        // 캘린더 생성 요청 객체에 영속 상태의 사용자 엔티티를 설정하여 캘린더를 생성
        request.setCalenderOwner(user);

        Calender savedCalender = calenderService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedCalender);
    }


    //Calender 전체 조회
    @GetMapping("/api/calenders")
    public ResponseEntity<List<CalenderResponse>> findAllCalender() {
        List<CalenderResponse> calenders = calenderService.findAll()
                .stream()
                .map(CalenderResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(calenders);
    }

    //Calender id로 단건 조회
    @GetMapping("/api/calenders/{id}")
    public ResponseEntity<CalenderResponse> findCalenderById(@PathVariable long id) {
        Calender calender = calenderService.findById(id);

        return ResponseEntity.ok()
                .body(new CalenderResponse(calender));
    }

    //Calender id로 삭제
    @DeleteMapping("/api/calenders/{id}")
    public ResponseEntity<Void> deleteCalender(@PathVariable long id) {
        calenderService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    //Calender 수정
    @PutMapping("/api/calenders/{id}")
    public ResponseEntity<Calender> updateArticle(@PathVariable long id,
                                                  @RequestBody UpdateCalenderRequest request) {
        Calender updatedCalender = calenderService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedCalender);
    }

}

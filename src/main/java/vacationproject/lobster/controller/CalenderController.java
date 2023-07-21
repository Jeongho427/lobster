package vacationproject.lobster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.dto.AddCalenderRequest;
import vacationproject.lobster.dto.CalenderResponse;
import vacationproject.lobster.service.CalenderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CalenderController {

    private final CalenderService calenderService;

    //Calender 생성
    @PostMapping("/api/calenders")
    public ResponseEntity<Calender> createCalender(@RequestBody AddCalenderRequest request) {
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


}

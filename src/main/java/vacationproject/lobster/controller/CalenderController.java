package vacationproject.lobster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vacationproject.lobster.domain.Calendar;
import vacationproject.lobster.dto.AddCalendarRequest;
import vacationproject.lobster.dto.CalendarResponse;
import vacationproject.lobster.dto.UpdateCalendarRequest;
import vacationproject.lobster.service.CalendarService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CalenderController {

    private final CalendarService calendarService;

    //Calender 생성
    @PostMapping("/api/calendars")
    public ResponseEntity<Calendar> createCalendar(@RequestBody AddCalendarRequest request) {
        Calendar savedCalendar = calendarService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedCalendar);
    }

    //Calender 전체 조회
    @GetMapping("/api/calendars")
    public ResponseEntity<List<CalendarResponse>> findAllCalendar() {
        List<CalendarResponse> calendars = calendarService.findAll()
                .stream()
                .map(CalendarResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(calendars);
    }

    //Calender id로 단건 조회
    @GetMapping("/api/calendars/{id}")
    public ResponseEntity<CalendarResponse> findCalendarById(@PathVariable long id) {
        Calendar calendar = calendarService.findById(id);

        return ResponseEntity.ok()
                .body(new CalendarResponse(calendar));
    }

    //Calender id로 삭제
    @DeleteMapping("/api/calendars/{id}")
    public ResponseEntity<Void> deleteCalender(@PathVariable long id) {
        calendarService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    //Calender 수정 => 일정 추가 및 수정
    @PostMapping("/api/calendars/{id}")
    public ResponseEntity<Calendar> updateArticle(@PathVariable long id,
                                                  @RequestBody UpdateCalendarRequest request) {
        Calendar updatedCalendar = calendarService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedCalendar);
    }

}

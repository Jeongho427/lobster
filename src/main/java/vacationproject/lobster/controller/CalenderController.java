package vacationproject.lobster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vacationproject.lobster.Security.JwtProvider;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.dto.calender.AddCalenderRequest;
import vacationproject.lobster.dto.calender.CalenderMonthRequest;
import vacationproject.lobster.dto.calender.CalenderResponse;
import vacationproject.lobster.dto.calender.UpdateCalenderRequest;
import vacationproject.lobster.service.CalenderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CalenderController {

    private final CalenderService calenderService;
    private final JwtProvider jwtProvider;

    // 일정 생성
    @PostMapping("/api/calenders")
    public ResponseEntity<Calender> createEvent(@RequestHeader HttpHeaders headers,
                                                @RequestBody AddCalenderRequest request) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtProvider.extractUIdFromToken(token);

        Calender savedCalender = calenderService.save(request, uId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedCalender);
    }

    // CalenderOwner id로 해당 사용자의 일정들 가져오기
    /*@GetMapping("/api/calenders")
    public ResponseEntity<List<CalenderResponse>> findUserCalenders(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtProvider.extractUIdFromToken(token);

        List<CalenderResponse> userCalenders = calenderService.findUserCalenders(uId)
                .stream()
                .map(CalenderResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(userCalenders);
    }*/

    // 특정 달 및 앞뒤 3달치 일정 가져오기
    @GetMapping("/api/calenders")
    public ResponseEntity<List<CalenderResponse>> getCalendersForMonth(@RequestHeader HttpHeaders headers,
                                                                       @RequestBody CalenderMonthRequest calenderMonthRequest) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtProvider.extractUIdFromToken(token);

        List<CalenderResponse> calendersForMonth = calenderService.getCalendersForMonth(uId,
                calenderMonthRequest.getYear(), calenderMonthRequest.getMonth());

        return ResponseEntity.ok()
                .body(calendersForMonth);
    }

    // 일정 삭제
    @DeleteMapping("/api/calenders")
    public ResponseEntity<Void> deleteEvent(@RequestHeader HttpHeaders headers,
                                            @RequestBody UpdateCalenderRequest calenderRequest) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtProvider.extractUIdFromToken(token);

        calenderService.delete(calenderRequest.getCId());

        return ResponseEntity.ok()
                .build();
    }

    // 일정 수정
    @PutMapping("/api/calenders")
    public ResponseEntity<Calender> updateEvent(@RequestHeader HttpHeaders headers,
                                                @RequestBody UpdateCalenderRequest calenderRequest) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtProvider.extractUIdFromToken(token);

        Calender updatedEvent = calenderService.update(calenderRequest.getCId(), calenderRequest);
        return ResponseEntity.ok(updatedEvent);
    }
}

package vacationproject.lobster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import vacationproject.lobster.service.CalenderService;

@RequiredArgsConstructor
@RestController
public class CalenderController {

    private final CalenderService calenderService;



}

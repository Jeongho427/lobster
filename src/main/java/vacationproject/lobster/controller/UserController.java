package vacationproject.lobster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import vacationproject.lobster.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

}

package com.project.hb.controller.user;

import com.project.hb.model.Token;
import com.project.hb.model.user.Role;
import com.project.hb.model.user.User;
import com.project.hb.service.user.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "Authorization, Content-Type", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.DELETE})
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate/host")
    public Token authenticateHost(@RequestBody User user) {
        return authenticationService.authenticate(user, Role.ROLE_HOST);
    }

    @PostMapping("/authenticate/guest")
    public Token authenticateGuest(@RequestBody User user) {
        return authenticationService.authenticate(user, Role.ROLE_GUEST);
    }
}

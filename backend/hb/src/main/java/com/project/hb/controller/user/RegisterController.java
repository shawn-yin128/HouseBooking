package com.project.hb.controller.user;

import com.project.hb.model.user.Role;
import com.project.hb.model.user.User;
import com.project.hb.service.user.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "Authorization, Content-Type", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.DELETE})
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class RegisterController {
    private RegisterService registerService;

    @PostMapping("/register/host")
    public void addHost(@RequestBody User user) {
        registerService.add(user, Role.ROLE_HOST);
    }

    @PostMapping("/register/guest")
    public void addGuest(@RequestBody User user) {
        registerService.add(user, Role.ROLE_GUEST);
    }
}

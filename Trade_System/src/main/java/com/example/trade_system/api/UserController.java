package com.example.trade_system.api;

import com.example.trade_system.models.User;
import com.example.trade_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) throws IOException {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest req) throws IOException {
        boolean success = userService.register(req.username(), req.password());
        return success ? "success" : "username-taken";
    }

    @PostMapping("/login")
    public User login(@RequestBody AuthRequest req) {
        User user = userService.login(req.username(), req.password());
        if (user == null) throw new RuntimeException("Invalid credentials");
        return user;
    }

}

package com.management.task.app.controllers;

import com.management.task.app.model.User;
import com.management.task.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/current-user")
    public ResponseEntity<String> getLoggerInUsers(Principal principal){
        return ResponseEntity.ok("{\"user_name\":\""+ principal.getName() +"\"}");
    }

}

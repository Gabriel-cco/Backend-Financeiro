package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User userResponse = userService.createUser(userDTO);
        return new ResponseEntity(userResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUser(User user){
        List<User> users = userService.findAllUsers();
        return new ResponseEntity(users, HttpStatus.OK);
    }

}

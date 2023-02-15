package com.example.springsecurity.Controller;

import com.example.springsecurity.DTO.Response;
import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthService myUserService;


    // All
    @PostMapping("/login")
    public ResponseEntity login(){
        return ResponseEntity.status(200).body("Logged in successfully");
    }

    // Admin
    @GetMapping("/all-users")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(myUserService.getAllUsers());
    }

    // Admin
    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(myUserService.getUser(id));
    }

    // All
    @GetMapping("/my-user")
    public ResponseEntity getMyUser(@AuthenticationPrincipal MyUser auth){
        return ResponseEntity.status(200).body(myUserService.getUser(auth.getId()));
    }

    // All
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody MyUser newUser){
        myUserService.addUser(newUser);
        return ResponseEntity.status(201).body("User Created");
    }

    // User - No one can update another user
    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody @Valid MyUser newUser, @AuthenticationPrincipal MyUser auth){
        myUserService.updateUser(newUser , auth.getId());
        return ResponseEntity.status(200).body("User Updated");
    }

    // Admin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        myUserService.deleteUser(id);
        return ResponseEntity.status(200).body("User Deleted");
    }

}
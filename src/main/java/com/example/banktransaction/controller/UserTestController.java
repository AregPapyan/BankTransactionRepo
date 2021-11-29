package com.example.banktransaction.controller;

import com.example.banktransaction.controller.dto.password.PasswordRequestModel;
import com.example.banktransaction.controller.dto.user.UserAdminModel;
import com.example.banktransaction.controller.dto.user.UserRequestModel;
import com.example.banktransaction.controller.dto.user.UserResponseModel;
import com.example.banktransaction.service.user.UserService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserTestController {
    private final UserService userService;

    public UserTestController(UserService userService) {
        this.userService = userService;
    }
    @PutMapping("/update")
    public ResponseEntity<UserResponseModel> update(@RequestBody UserRequestModel request, Authentication authentication){
        Long id = userService.getIdByAuthentication(authentication);
        return ResponseEntity.ok(userService.update(request, id));
    }
    @PutMapping("/user/activate/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserAdminModel> activate(@PathVariable Long id){
        return ResponseEntity.ok(userService.activate(id));
    }
    @PutMapping("/user/deactivate/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserAdminModel> deactivate(@PathVariable Long id){
        return ResponseEntity.ok(userService.deactivate(id));
    }
    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<UserAdminModel>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseModel> get(@PathVariable Long id){
        return ResponseEntity.ok(userService.get(id));
    }
    @PostMapping("/user")
    public ResponseEntity<UserResponseModel> add(@RequestBody UserRequestModel request){
        return ResponseEntity.ok(userService.add(request));
    }
    @PutMapping("/user/password")
    public ResponseEntity<UserResponseModel> updatePassword(@RequestBody PasswordRequestModel request, Authentication authentication) throws NotFoundException {
        Long idByAuthentication = userService.getIdByAuthentication(authentication);
        return ResponseEntity.ok(userService.updatePassword(request,idByAuthentication));
    }
    @GetMapping("/login")
    public ResponseEntity<UserResponseModel> login(Authentication authentication){
        Long id = userService.getIdByAuthentication(authentication);
        return ResponseEntity.ok(userService.get(id));
    }
}

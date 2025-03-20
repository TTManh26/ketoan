package org.hello.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.Setter;
import org.hello.dto.request.UserCreationRequest;
import org.hello.dto.request.UserUpdateRequest;
import org.hello.entity.User;
import org.hello.service.UserService;
import com.tvd12.ezyhttp.core.response.ResponseEntity;

import java.util.List;

@Setter
@Controller("/users")
public class UserController {
    @EzyAutoBind
    private UserService userService;

    @DoPost("/user")
    public User createUser(@RequestBody UserCreationRequest request){
        return userService.createUser(request);
    }
    @DoGet("/user")
    public List<User> getUser(){
        return userService.getUser();
    }
    @DoGet("/user/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return userService.getUser(id);
    }
    @DoPut("/user/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody UserUpdateRequest request){
        return userService.updateUser(id, request);
    }

    @DoDelete("/user/{id}")
    public ResponseEntity deleteEmployee(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
package com.shetu.user_management_app.controller;

import com.shetu.user_management_app.dto.request.UserRequestDTO;
import com.shetu.user_management_app.dto.response.ApiResponse;
import com.shetu.user_management_app.model.User;
import com.shetu.user_management_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/*todo: Add validation on each parameter: @Valid*/
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*@PostMapping("/add")
    public ResponseEntity<ApiResponse> saveUser(@RequestBody User user) throws Exception {
        return userService.addUser(user);
    }*/

    /*add @Valid*/
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveUser(@Valid @RequestBody UserRequestDTO userRequestDTO) throws Exception {
        return userService.addUser(userRequestDTO);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Integer id) throws Exception {
        return userService.retrieveOneUserById(id);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAllUsers() {
        return userService.retrieveAllUsers();
    }

    @GetMapping("/findAllByUserType/{userType}")
    public ResponseEntity<List<User>> findAllUsers(@PathVariable("userType") String userType) throws Exception {
        return userService.retrieveUsersByUserType(userType);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestParam(value = "parentUserId",required = false) Integer parentUserId, @RequestBody User user) throws Exception {
        return userService.updateOneUser(user, parentUserId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("id") Integer id) throws Exception {
        return userService.removeOneUserById(id);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse> deleteAll(){
        return userService.removeAllUsers();
    }
}

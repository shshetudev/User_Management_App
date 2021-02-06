package com.shetu.user_management_app.service_impl;

import com.shetu.user_management_app.dto.request.UserRequestDTO;
import com.shetu.user_management_app.dto.response.ApiResponse;
import com.shetu.user_management_app.model.User;
import com.shetu.user_management_app.service.UserService;
import com.shetu.user_management_app.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl extends UserService {
    public int demoReturnTwo() {
        return 2;
    }
    @Override
    public ResponseEntity<ApiResponse> addUser(UserRequestDTO userRequestDTO) throws Exception {
        User user = isChild(userRequestDTO) ? saveChildUser(userRequestDTO) : saveParentUser(userRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), Constants.USER_SAVED, user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> retrieveOneUserById(Integer id) throws Exception {
        User foundUser = findById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), Constants.USER_RETRIEVED, foundUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<User>> retrieveAllUsers() {
        List<User> allUsers = findAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<User>> retrieveUsersByUserType(String userType) throws Exception {
        List<User> retrievedUsers = findAllUsersByUserType(userType);
        return new ResponseEntity<>(retrievedUsers, HttpStatus.OK);
    }

    /*todo: Check updating properly*/
    @Override
    public ResponseEntity<ApiResponse> updateOneUser(User user, Integer parentUserId) throws Exception {
        user = updateUser(user, parentUserId);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), Constants.USER_UPDATED, user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> removeOneUserById(Integer id) throws Exception {
        User deletedUser = deleteUserById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), Constants.USER_DELETED, deletedUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> removeAllUsers() {
        List<User> users = new ArrayList<>();
        deleteAllUsers();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), Constants.ALL_USERS_DELETED, users), HttpStatus.OK);
    }
}

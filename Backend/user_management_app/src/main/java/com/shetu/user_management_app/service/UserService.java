package com.shetu.user_management_app.service;

import com.shetu.user_management_app.dto.request.UserRequestDTO;
import com.shetu.user_management_app.dto.response.ApiResponse;
import com.shetu.user_management_app.enums.ErrorCodes;
import com.shetu.user_management_app.enums.UserType;
import com.shetu.user_management_app.exception.BadRequestException;
import com.shetu.user_management_app.exception.InvalidActionException;
import com.shetu.user_management_app.exception.ResourceNotFoundException;
import com.shetu.user_management_app.model.User;
import com.shetu.user_management_app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.*;

@Slf4j
public abstract class UserService {
    @Autowired
    private UserRepository userRepository;

    // Abstract Methods
    public abstract ResponseEntity<ApiResponse> addUser(UserRequestDTO userRequestDTO) throws Exception;

    public abstract ResponseEntity<ApiResponse> retrieveOneUserById(Integer id) throws Exception;

    public abstract ResponseEntity<List<User>> retrieveAllUsers();

    public abstract ResponseEntity<List<User>> retrieveUsersByUserType(String userType) throws Exception;

    public abstract ResponseEntity<ApiResponse> updateOneUser(User user, Integer parentUserId) throws Exception;

    public abstract ResponseEntity<ApiResponse> removeOneUserById(Integer id) throws Exception;

    public abstract ResponseEntity<ApiResponse> removeAllUsers();

    // Concrete Methods
    // Create
    /*Child User Must have Parent, But Child is not a must toParent User*/
    public List<User> saveAll(List<User> users) throws Exception {
        if (users == null || users.size() <= 0) throw new InvalidActionException(ErrorCodes.U0006);
        users = userRepository.saveAll(users);
        log.info("Saved Users size: {}", users.size());
        return users;
    }

    public User saveUserToDB(User user) throws Exception {
        if (user == null) throw new BadRequestException(ErrorCodes.U0002);
        user = userRepository.save(user);
        log.info("UserType: {} with Id: {} is saved", user.getUserType(), user.getId());
        return user;
    }

    public void setParentToChildUsers(User parentUser, List<User> childUsers) throws Exception {
        parentUser.setChildUsers(childUsers);
        parentUser = saveUserToDB(parentUser);
        log.info("Parent Id:{}, Child Users size:{}", parentUser.getId(), childUsers.size());
    }

    public User saveParentUser(UserRequestDTO userRequestDTO) throws Exception {
        User user = convertUserDTOToUser(userRequestDTO);
        user = saveUserToDB(user);
        return user;
    }

    public User saveChildUser(UserRequestDTO userRequestDTO) throws Exception {
        if (userRequestDTO.getParentUserId() == null) throw new ResourceNotFoundException(null,ErrorCodes.U0005);
        List<User> userList = new ArrayList<>();
        User childUser = convertUserDTOToUser(userRequestDTO);
        userList.add(childUser);
        childUser = saveUserToDB(childUser);
        User parentUser = findById(userRequestDTO.getParentUserId());
        setParentToChildUsers(parentUser, userList);
        return childUser;
    }


    // Read
    public User findById(Integer id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, ErrorCodes.U0000));
        log.info("user found by id:{} is: {}", user.getId(), user);
        return user;
    }

    public User findByIdAndUserType(Integer id, UserType userType) throws Exception {
        User user = userRepository.findByIdAndUserType(id, userType).orElseThrow(() -> new ResourceNotFoundException(id, ErrorCodes.U0000));
        log.info("user found by id:{} and User Type:{} is: {}", user.getId(), user.getUserType(), user);
        return user;
    }

    public List<User> findAllUsersByUserType(String userType) throws Exception {
        List<User> users = new ArrayList<>();
        UserType validUserType;
        if (userType == null) throw new InvalidActionException(ErrorCodes.U0004);

        try {
            validUserType = UserType.valueOf(userType.toUpperCase());
        } catch (Exception exception) {
            throw new BadRequestException(ErrorCodes.U0004);
        }

        switch (validUserType) {
            case CHILD:
                users = findAllChildUsers();
                break;
            case PARENT:
                users = findAllParentUsers();
                break;
        }
        return users;
    }

    public List<User> findAllParentUsers() {
        List<User> parentUsers = userRepository.findAllByUserType(UserType.PARENT);
        log.info("Parent Users size: {}", parentUsers.size());
        return parentUsers;
    }

    public List<User> findAllChildUsers() {
        List<User> childUsers = userRepository.findAllByUserType(UserType.CHILD);
        log.info("Child Users size: {}", childUsers.size());
        return childUsers;
    }


    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("All Users size:{}", users);
        return users;
    }

    // Update
    public User updateUser(User userWithUpdatedInfo, Integer parentUserId) throws Exception {
        if (userWithUpdatedInfo == null || userWithUpdatedInfo.getId() == null)
            throw new BadRequestException(ErrorCodes.U0001);
        /*if (!doesUserExist(user)) throw new ResourceNotFoundException(user.getId().toString(), ErrorCodes.U0002);*/
        User userFromDB = findById(userWithUpdatedInfo.getId());

        if (!userWithUpdatedInfo.getUserType().equals(userFromDB.getUserType())) {
            if (isParent(userFromDB)) {
                if (isParentWithNoChildren(userFromDB)) {
                    userWithUpdatedInfo = updateParentUserWithoutChildUsers(userWithUpdatedInfo, parentUserId);
                } else {
                    userWithUpdatedInfo = updateParentUserWithChildUsers(userFromDB, userWithUpdatedInfo, parentUserId);
                }
            } else {
                userWithUpdatedInfo = updateChildUser(userFromDB.getId(), userWithUpdatedInfo);
            }
        }
        userFromDB = userRepository.save(userWithUpdatedInfo);
        log.info("Updated user:{}", userFromDB);
        return userFromDB;
    }

    public User changeParentUserToChildUser(User parentUser, Integer parentUserId) throws Exception {
        User parentUserFromDB = findByIdAndUserType(parentUserId, UserType.PARENT);
        parentUser.setUserType(UserType.CHILD);
        List<User> childUserList = new ArrayList<>();
        childUserList.add(parentUser);
        setParentToChildUsers(parentUserFromDB, childUserList);
        return parentUser;
    }

    public User updateParentUserWithoutChildUsers(User parentUser, Integer parentUserId) throws Exception {
        if (parentUserId == null) throw new ResourceNotFoundException(parentUserId, ErrorCodes.U0005);
        changeParentUserToChildUser(parentUser, parentUserId);
        return parentUser;
    }

    public User updateParentUserWithChildUsers(User userFromDB, User parentUser, Integer parentUserId) throws Exception {
        if (parentUserId == null) throw new ResourceNotFoundException(parentUserId, ErrorCodes.U0005);
        List<User> childUsers = parentUser.getChildUsers();
        if (parentUser.getChildUsers() != null) {
            parentUser.setChildUsers(null);
            childUsers.forEach(childUser -> childUser.setUserType(UserType.PARENT));
            saveAll(childUsers);
        }
        changeParentUserToChildUser(parentUser, parentUserId);
        return parentUser;
    }

    public User updateChildUser(Integer userIdFromDB, User childUserWithUpdatedInfo) {
        userRepository.deleteChildUserFromParentChildTable(userIdFromDB);
        childUserWithUpdatedInfo.setUserType(UserType.PARENT);
        return childUserWithUpdatedInfo;
    }

    private boolean doesUserExist(User user) {
        boolean doesExist = false;
        if (user.getId() != null && userRepository.existsById(user.getId())) {
            doesExist = true;
        }
        log.info("User existing status: {}, for user:{}", doesExist, user);
        return doesExist;
    }
   /* public User updateParentToChildUser(User user) {
    }

    public User updateChildToParentUser(User user) {
    }*/

    // Delete
    public User deleteUserById(Integer id) throws Exception {
        if (id == null) throw new InvalidActionException(ErrorCodes.U0003);
        User user = findById(id);
        boolean deleteStatus = isParent(user) ? deleteParentUser(user) : deleteChildUser(user);
        String deletionStatus = deleteStatus ? " is deleted successfully " : "is not deleted ";
        log.info("user of userType:{}, with id:{}" + deletionStatus, user.getUserType(), user.getId());
        return user;
    }

    //    @Transactional
    public boolean deleteChildUser(User user) {
        userRepository.deleteChildUserFromParentChildTable(user.getId());
        userRepository.deleteChildUserFromUserTable(user.getId());
        return true;

    }

    public boolean deleteParentUser(User user) throws Exception {
        boolean isStateChanged = changeUserTypeOfChildUsersFromChildToParent(user);
        user.setChildUsers(null);
        userRepository.deleteById(user.getId());
        String validatedString = isStateChanged ? " is " : " is not ";
        log.info("Child Users of Parent User with id: {}" + validatedString + "changed to Parent User", user.getId());
        return isStateChanged;
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    private boolean changeUserTypeOfChildUsersFromChildToParent(User parentUser) throws Exception {
        boolean stateChangeStatus = false;
        if (parentUser.getChildUsers() != null) {
            List<User> childUsers = parentUser.getChildUsers();
            childUsers.forEach(user -> user.setUserType(UserType.PARENT));
            childUsers = saveAll(childUsers);
            stateChangeStatus = true;
        }
        return stateChangeStatus;
    }


    // User Utiliy methods
    public User convertUserDTOToUser(UserRequestDTO userRequestDTO) throws Exception {
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setUserType(UserType.valueOf(userRequestDTO.getUserType().toUpperCase()));
        user.setStreet(userRequestDTO.getStreet());
        user.setCity(userRequestDTO.getCity());
        user.setState(userRequestDTO.getState());
        user.setZipCode(userRequestDTO.getZipCode());
        user.setChildUsers(userRequestDTO.getChildUsers());
        return user;
    }

    public boolean isChild(UserRequestDTO userRequestDTO) {
        return userRequestDTO.getUserType().equals(UserType.CHILD.name());
    }

    public boolean isChild(User user) {
        return user.getUserType().equals(UserType.CHILD);
    }

    public boolean isParent(UserRequestDTO userRequestDTO) {
        return userRequestDTO.getUserType().equals(UserType.PARENT.name());
    }

    public boolean isParent(User user) {
        return user.getUserType().equals(UserType.PARENT);
    }

    public boolean isParentWithNoChildren(User user) {
        return user.getChildUsers() == null || user.getChildUsers().size() <= 0;
    }
}

package com.shetu.user_management_app.service;

import com.shetu.user_management_app.dto.request.UserRequestDTO;
import com.shetu.user_management_app.enums.UserType;
import com.shetu.user_management_app.exception.ResourceNotFoundException;
import com.shetu.user_management_app.model.User;
import com.shetu.user_management_app.repository.UserRepository;
import com.shetu.user_management_app.service_impl.UserServiceImpl;
import com.shetu.user_management_app.UnitTestUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class TestUserService {
    /*
     * todo: Know Rule: AAA
     *  Arrange
     *  Action
     *  Assertion
     * */

    /*
    * todo:
    *  // First create a Demo User
        // inside mockito when call repository layer method and return that demo user
        // call the service layer method which uses the same repository layer method
        // Make assertions on: created Demo user and service layer result
    * */

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;


    @BeforeAll
    @DisplayName("Service Layer: Set up All")
    public static void setUpAll() throws Exception {
        log.info("**** Service layer test starts  *****");
    }

    /*User Creation Test*/
    @Test
    @Order(1)
    @DisplayName("Not Saving Parent User Due To Null First Name")
    public void testNotSaveParentDueToNullFirstName() throws Exception {
        // Arrange
        User insertedUser = UnitTestUtils.createdFirstParentUserWithoutChildUsersFromDB();
        insertedUser.setFirstName(null);
        Mockito.when(userRepository.save(insertedUser)).thenThrow(ConstraintViolationException.class);
        // Action and Assertion
        Assertions.assertThrows(ConstraintViolationException.class,() -> userService.saveUserToDB(insertedUser));
    }


    @Test
    @Order(2)
    @DisplayName("Saving Parent User")
    public void testSaveParent() throws Exception {
        // Arrange
        User insertedUser = UnitTestUtils.createdFirstParentUserWithoutChildUsersFromDB();
        Mockito.when(userRepository.save(insertedUser))
                .thenReturn(insertedUser);
        // Action
        User foundUser = userService.saveUserToDB(insertedUser);
        // Assertion
        Assertions.assertEquals(insertedUser.getFirstName(), foundUser.getFirstName());
    }

    @Test
    @Order(3)
    @DisplayName("Not Saving Child User Due to No Valid Parent User")
    public void testNotSaveChildDueToNoParentUser() throws Exception {
        // Arrange
        UserRequestDTO childUserRequestDTOWithParentUserId = UnitTestUtils.createChildUserRequestDTOWithoutParentUserId();
        // Action
        // Assertion
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.saveChildUser(childUserRequestDTOWithParentUserId));
    }

    @Test
    @Order(4)
    @DisplayName("Saving Child User Under ANon Existing Parent User")
    public void testSaveChildUnderANonExistingParentUser() throws Exception {
        // Arrange
        User parentUserWithChildUsers = UnitTestUtils.createSecondParentUserWithChildUsers();
        Mockito.when(userRepository.save(parentUserWithChildUsers))
                .thenReturn(parentUserWithChildUsers);
        // Action
        int expectedSize = parentUserWithChildUsers.getChildUsers().size();
        int actualSize = userService.saveUserToDB(parentUserWithChildUsers).getChildUsers().size();
        /*User childUser = userService.addUser(parentUser);*/
        // Assertion
        Assertions.assertEquals(expectedSize, actualSize);
    }

     /*User Read Test*/

    @Test
    @Order(5)
    @DisplayName("Not Find User By Id")
    public void testNotFindUserById() throws Exception {
        Mockito.when(userRepository.findById(2)).thenThrow(NullPointerException.class);
        Assertions.assertThrows(NullPointerException.class,() -> userService.findById(2));
    }

    @Test
    @Order(6)
    @DisplayName("Find User By Id")
    public void testFindUserById() throws Exception {
        User createdUser = UnitTestUtils.createFirstParentUserWithoutChildUsers();
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(createdUser));
        User foundUser = userService.findById(1);
        Assertions.assertEquals(createdUser.getFirstName(),foundUser.getFirstName());
    }

    @Test
    @Order(7)
    @DisplayName("Not Find User By Id and UserType")
    public void testNotFindByIdAndUserType() throws Exception {
        // Arrange
        User parentUserWithoutChildUsers = UnitTestUtils.createFirstParentUserWithoutChildUsers();
        Mockito.when(userRepository.findByIdAndUserType(3,UserType.PARENT)).thenReturn(Optional.of(parentUserWithoutChildUsers));
        Assertions.assertEquals("Belal", userService.findByIdAndUserType(3,UserType.PARENT).getFirstName());
    }


    @Test
    @Order(8)
    @DisplayName("Find User By Id and UserType")
    public void testFindByIdAndUserType() throws Exception {
        User parentUserWithoutChildUsers = UnitTestUtils.createFirstParentUserWithoutChildUsers();
        Mockito.when(userRepository.findByIdAndUserType(3,UserType.PARENT)).thenReturn(Optional.of(parentUserWithoutChildUsers));
        Assertions.assertEquals("Belal", userService.findByIdAndUserType(3,UserType.PARENT).getFirstName());
    }


    @Test
    @Order(9)
    @DisplayName("Find All Parent Users")
    public void testFindAllParentUsers(){
        // Arrange
        User parentUserWithoutChild = UnitTestUtils.createdFirstParentUserWithoutChildUsersFromDB();
        User parentUserWithChild = UnitTestUtils.createSecondParentUserWithChildUsers();
        List<User> userList = new ArrayList<>();
        userList.add(parentUserWithChild);
        userList.add(parentUserWithoutChild);

        Mockito.when(userRepository.findAllByUserType(UserType.PARENT)).thenReturn(userList);
        // Action and Assertion
        Assertions.assertEquals(2,userService.findAllParentUsers().size());
    }

    @Test
    @Order(10)
    @DisplayName("Find All Child Users")
    public void testFindAllChildUsers(){
        User firstChildUser = UnitTestUtils.createFirstChildUserAttachedToSecondParentUser();
        User secondChildUser = UnitTestUtils.createSecondChildUserAttachedToSecondParentUser();
        List<User> userList = new ArrayList<>();
        userList.add(firstChildUser);
        userList.add(secondChildUser);

        Mockito.when(userRepository.findAllByUserType(UserType.CHILD)).thenReturn(userList);
        // Action and Assertion
        Assertions.assertEquals(2,userService.findAllChildUsers().size());
    }

    @Test
    @Order(11)
    @DisplayName("Find All Users")
    public void testFindAllUsers(){
        User parentUserWithoutChild = UnitTestUtils.createdFirstParentUserWithoutChildUsersFromDB();
        User parentUserWithChild = UnitTestUtils.createSecondParentUserWithChildUsers();
        User firstChildUser = UnitTestUtils.createFirstChildUserAttachedToSecondParentUser();
        User secondChildUser = UnitTestUtils.createSecondChildUserAttachedToSecondParentUser();
        List<User> userList = new ArrayList<>();
        userList.add(parentUserWithChild);
        userList.add(parentUserWithoutChild);
        userList.add(firstChildUser);
        userList.add(secondChildUser);

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        // Action and Assertion
        Assertions.assertEquals(4,userService.findAllUsers().size());
    }

    /*User Update Test*/
    @Test
    @Order(12)
    @DisplayName("Not Change User Type of Parent User To Child Having No ChildUser Due To No Parent User Existing in DB")
    public void testNotChangeUserTypeOfParentUserToChildHavingNoChildUserDueToNoParentUserExistingInDB(){
        // Arrange
        User parentUserWithoutChild = UnitTestUtils.createdFirstParentUserWithoutChildUsersFromDB();
        parentUserWithoutChild.setUserType(UserType.CHILD);
        Integer nonExistingParentUserId = null;
        // Action
        // Assertion
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.updateParentUserWithoutChildUsers(parentUserWithoutChild,nonExistingParentUserId));
    }


    /*User Delete Test*/
    @Test
    @Order(13)
    @DisplayName("Not Delete Parent User Having No Child User Due To Invalid User Id")
    public void testNotDeleteParentUserHavingNoChildUserDueToInvalidUserId(){
        // Arrange
        User parentUser = UnitTestUtils.createFirstParentUserWithoutChildUsers();
        Mockito.when(userRepository.findById(parentUser.getId())).thenThrow(ResourceNotFoundException.class);
        // Action
        // Assertion
        Assertions.assertThrows(ResourceNotFoundException.class,() -> userService.deleteUserById(parentUser.getId()));
    }

    @Test
    @Order(14)
    @DisplayName("Delete Parent User Having No Child User")
    public void testDeleteParentUserHavingNoChildUser() throws Exception {
        // Arrange
        User parentUser = UnitTestUtils.createFirstParentUserWithoutChildUsers();
        Mockito.when(userRepository.findById(parentUser.getId())).thenReturn(Optional.of(parentUser));
        // Action
        // Assertion
        Assertions.assertEquals(parentUser, userService.deleteUserById(parentUser.getId()));
    }

    @Test
    @Order(15)
    @DisplayName("Not Delete Child User Due To Invalid UserId")
    public void testNotDeleteChildUserDueToInvalidUserId(){
        // Arrange
        User childUser = UnitTestUtils.createSecondChildUserAttachedToSecondParentUser();
        Mockito.when(userRepository.findById(childUser.getId())).thenThrow(ResourceNotFoundException.class);
        // Action
        // Assertion
        Assertions.assertThrows(ResourceNotFoundException.class,() -> userService.deleteUserById(childUser.getId()));
    }

    @Test
    @Order(16)
    @DisplayName("Delete Child User")
    public void testDeleteChildUser() throws Exception {
        // Arrange
        User childUser = UnitTestUtils.createSecondChildUserAttachedToSecondParentUser();
        Mockito.when(userRepository.findById(childUser.getId())).thenReturn(Optional.of(childUser));
        // Action
        // Assertion
        Assertions.assertEquals(childUser, userService.deleteUserById(childUser.getId()));
    }

    @AfterAll
    @DisplayName("Service Layer: Tear Down All")
    public static void tearDownAll() {
        log.info("**** Service layer test ends  *****");
    }
}

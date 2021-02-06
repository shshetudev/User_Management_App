package com.shetu.user_management_app.repository;

import com.shetu.user_management_app.enums.UserType;
import com.shetu.user_management_app.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*todo: 3 rules to follow: Arrange, Act, Assert
 * */
@SpringBootTest
//@RunWith(JUnitPlatform.class)
//@DataJpaTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserRepository {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    @DisplayName("Set up All")
    public static void setUpAll() throws Exception {
        log.info("**** Repository layer test starts  *****");
    }

    /*::::::::::::: Testing: Creation Starts::::::::::::::::::::::::*/
    @Test
    @Order(1)
    @DisplayName("Injected Components are not null")
    public void injectedComponentsAreNotNull() {
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(jdbcTemplate);
        Assertions.assertNotNull(entityManager);
        Assertions.assertNotNull(userRepository);
    }

    @Test
    @Order(2)
    @DisplayName("User Saving failed due to first name being null")
    public void testNotSavingUserDueToNullFirstName() {
        // Arrange
        User user = createParentUser();
        user.setFirstName(null);
        // Action and Assertion
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user), "Throws Exception due to first name being null");
    }

    @Test
    @Order(3)
    @DisplayName("User Saving failed due to user type being null")
    public void testNotSavingUserDueToNullUserType() {
        // Arrange
        User user = createParentUser();
        user.setUserType(null);
        // Action and Assertion
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user), "Throws Exception due to user type being null");
    }

    @Test
    @Order(4)
    @DisplayName("Save Parent User")
    public void testSaveParentUser() {
        // Arrange
        User parentUser = createParentUser();
        // Action and Assertion
        parentUser = userRepository.save(parentUser);
        log.info("Parent User: {}", parentUser);
        Assertions.assertEquals("Shahariar", parentUser.getFirstName());
    }

    private User createParentUser() {
        User parentUser = new User();
        parentUser.setFirstName("Shahariar");
        parentUser.setLastName("Shetu");
        parentUser.setStreet("Mirpur");
        parentUser.setCity("Dhaka");
        parentUser.setState("Bangladesh");
        parentUser.setZipCode("1216");
        parentUser.setUserType(UserType.PARENT);
        return parentUser;
    }

    @Test
    @Order(5)
    @DisplayName("Save 2 Child Users under the Parent User")
    public void testSaveChildUsers() {
        // Arrange
        User firstChildUser = createFirstChildUser();
        User secondChildUser = createSecondChildUser();
        User parentUser = findUserByFirstNameAndUserType("Shahariar", UserType.PARENT);
        // Action and Assertion
        boolean isSaved = saveChildUsersAndSetThemToParentUser(parentUser, setAndGetUserList(firstChildUser, secondChildUser));
        Assertions.assertTrue(isSaved, "Child Users are saved successfully under parent User");
    }

    private User createFirstChildUser() {
        User childUser = new User();
        childUser.setFirstName("Mehnaz");
        childUser.setLastName("Chowdhury");
        childUser.setStreet("Mirpur");
        childUser.setCity("Dhaka");
        childUser.setState("Bangladesh");
        childUser.setZipCode("1216");
        childUser.setUserType(UserType.CHILD);
        return childUser;
    }

    private User createSecondChildUser() {
        User childUser = new User();
        childUser.setFirstName("Nadia");
        childUser.setLastName("Sultana");
        childUser.setStreet("Hazigonj");
        childUser.setCity("Chandpur");
        childUser.setState("Bangladesh");
        childUser.setZipCode("1234");
        childUser.setUserType(UserType.CHILD);
        return childUser;
    }

    private List<User> setAndGetUserList(User... childUsers) {
        List<User> users = new ArrayList<>(Arrays.asList(childUsers));
        return users;
    }

    private boolean saveChildUsersAndSetThemToParentUser(User parentUser, List<User> childUsers) {
        boolean isSaved = true;
        if (parentUser.getId() != null) {
            childUsers = userRepository.saveAll(childUsers);
            log.info("Number of saved child users:{}, Saved Child Users:{}", childUsers.size(), childUsers);
            parentUser.setChildUsers(childUsers);
            parentUser = userRepository.save(parentUser);
            log.info("Saved Parent User: {}", parentUser);
        } else {
            isSaved = false;
            log.info("Parent User not found");
        }
        return isSaved;
    }
    /*::::::::::::: Testing: Creation Ends::::::::::::::::::::::::*/

    /*:::::::::  Testing: Fetch Starts :::::::::::::::::::::*/

    /*todo: Add new parent and child users if we want to test it solely */
    @Test
    @Order(6)
    @DisplayName("Finding Invalid Parent User")
    public void testNotFindingValidParentUser() {
        // Arrange
        String firstName = "Shetu";
        User userFromDB = findUserByFirstNameAndUserType(firstName, UserType.PARENT);
        Assertions.assertNull(userFromDB.getId(), "User with firstName: " + firstName + " is not a Parent User");
    }


    @Test
    @Order(7)
    @DisplayName("Finding Valid Parent User")
    public void testFindingValidParentUser() {
        String firstName = "Shahariar";
        User userFromDB = findUserByFirstNameAndUserType(firstName, UserType.PARENT);
        Assertions.assertEquals(firstName, userFromDB.getFirstName(), "User with firstName: " + firstName + " is a Parent User");
    }

    @Test
    @Order(8)
    @DisplayName("Finding Invalid Child User")
    public void testNotFindingValidChildUser() {
        String firstName = "Shahariar";
        User userFromDB = findUserByFirstNameAndUserType(firstName, UserType.CHILD);
        Assertions.assertNull(userFromDB.getId(), "User with firstName: " + firstName + " is not a Child User");
    }


    @Test
    @Order(9)
    @DisplayName("Finding Valid Parent User")
    public void testFindingValidChildUser() {
        String firstName = "Mehnaz";
        User userFromDB = findUserByFirstNameAndUserType(firstName, UserType.CHILD);
        Assertions.assertEquals(firstName, userFromDB.getFirstName(), "User with firstName: " + firstName + " is a Child User");
    }

    @Test
    @Order(10)
    @DisplayName("Not Finding By First Name And UserType DueTo Wrong First Name")
    public void testNotFindingByFirstNameAndUserTypeDueToWrongFirstName() {
        String firstName = "Shetu";
        User userFromDB = findUserByFirstNameAndUserType(firstName, UserType.PARENT);
        Assertions.assertNull(userFromDB.getId(), "User is Not found by firstName: " + firstName);
    }

    @Test
    @Order(11)
    @DisplayName("Not Finding By First Name And UserType DueTo Wrong User Type")
    public void testNotFindingByFirstNameAndUserTypeDueToWrongUserType() {
        String firstName = "Shahariar";
        User userFromDB = findUserByFirstNameAndUserType(firstName, UserType.CHILD);
        Assertions.assertNull(userFromDB.getId(), "User is Not found by UserType: " + UserType.CHILD.name());
    }

    private User findUserByFirstNameAndUserType(String firstName, UserType userType) {
        return userRepository.findByFirstNameAndUserType(firstName, userType).orElseGet(User::new);
    }

    @Test
    @Order(12)
    @DisplayName("Not Finding User By Id due to wrong User Id")
    public void testNotFindingById() {
        // Arrange
        Integer wrongId = 0;
        // Action
        User user = userRepository.findById(wrongId).orElse(null);
        // Assertion
        Assertions.assertNull(user, "Not Finding User By Id: " + wrongId);
    }

    @Test
    @Order(13)
    @DisplayName("Finding User By Id")
    public void testFindById() {
        // Arrange
        String firstName = "Shahariar";
        UserType parentUserType = UserType.PARENT;
        User userFromDB = findUserByFirstNameAndUserType(firstName, parentUserType);
        Integer id = userFromDB.getId();
        // Action
        User user = userRepository.findById(id).orElseGet(User::new);
        // Assertion
        Assertions.assertEquals(id, user.getId(), "Found User By Id: " + id);
    }


    @Test
    @Order(14)
    @DisplayName("Not Finding All Parent Users Due to Invalid UserType")
    public void testNotFindingAllParentUsers() {
        // Arrange
        // Action
        List<User> wrongParentUsers = userRepository.findAllByUserType(UserType.CHILD);
        // Assertion
        Assertions.assertNotEquals(1, wrongParentUsers.size(), "Parent Users are not found");
    }

    @Test
    @Order(15)
    @DisplayName("Finding All Parent Users Due to Valid UserType")
    public void testFindingAllParentUsers() {
        // Arrange
        // Action
        List<User> parentUsers = userRepository.findAllByUserType(UserType.PARENT);
        // Assertion
        Assertions.assertEquals(1, parentUsers.size(), "Parent Users are found");
    }

    @Test
    @Order(16)
    @DisplayName("Not Finding All Child Users Due to Invalid UserType")
    public void testNotFindingAllChildUsers() {
        // Arrange
        // Action
        List<User> wrongChildUsers = userRepository.findAllByUserType(UserType.PARENT);
        // Assertion
        Assertions.assertNotEquals(2, wrongChildUsers.size(), "Child Users are not found");
    }

    @Test
    @Order(17)
    @DisplayName("Finding All Child Users Due to Valid UserType")
    public void testFindingAllChildUsers() {
        // Arrange
        // Action
        List<User> childUsers = userRepository.findAllByUserType(UserType.CHILD);
        // Assertion
        Assertions.assertEquals(2, childUsers.size(), "Child Users are found");
    }

    @Test
    @Order(18)
    @DisplayName("Finding All Child Users of a Parent User")
    @Transactional
    public void testFindingAllChildUsersOfAParentUser() {
        // Arrange
        // Action
        User parentUser = findUserByFirstNameAndUserType("Shahariar", UserType.PARENT);
        Integer numberOfChildren = parentUser.getChildUsers().size();
        // Assertion
        Assertions.assertEquals(2, numberOfChildren, "Parent User: " + parentUser.getFirstName() + " has " + numberOfChildren + " child users");
    }

    @Test
    @Order(19)
    @DisplayName("Finding All Users")
    public void testFindingAll() {
        List<User> allUsers = userRepository.findAll();
        Assertions.assertEquals(3,allUsers.size());
    }
    /*:::::::::  Testing: Fetch Ends :::::::::::::::::::::*/

    /*:::::::::  Testing: Update Starts :::::::::::::::::::::*/
    /*@Test
    @Order(5)
    @DisplayName("Not Updating Users")
    public void testNotUpdatingUser() {
    }*/

    @Test
    @Order(20)
    @DisplayName("Update User's Last Name")
    public void testUpdatingUser() {
        User user = findUserByFirstNameAndUserType("Shahariar",UserType.PARENT);
        user.setLastName("Hossen");
        user = userRepository.save(user);
        Assertions.assertEquals("Hossen", user.getLastName(),"Users Last Name is updated");
    }
    /*:::::::::  Testing: Update Ends :::::::::::::::::::::*/

    /*:::::::::  Testing: Deletion Starts :::::::::::::::::::::*/
    @Order(21)
    @DisplayName("Delete Parent User By Id")
    @Test
    public void testDeleteParentUserById() {
        User createdParentUser = createParentUser();
        createdParentUser.setFirstName("Shafayet");
        createdParentUser = userRepository.save(createdParentUser);
        userRepository.deleteById(createdParentUser.getId());
        Assertions.assertFalse(userRepository.existsById(createdParentUser.getId()));
    }

    @Test
    @Order(22)
    @DisplayName("Delete Child Users By Id")
    @Transactional
    public void testDeleteChildUsersById() {
        User createdParentUser = createParentUser();
        createdParentUser.setFirstName("Shafayet");
        createdParentUser = userRepository.save(createdParentUser);
        User firstChildUser = createFirstChildUser();
        firstChildUser.setFirstName("Zahid");
        saveChildUsersAndSetThemToParentUser(createdParentUser, Collections.singletonList(firstChildUser));
        firstChildUser = userRepository.findByFirstNameAndUserType(firstChildUser.getFirstName(),UserType.CHILD).orElseGet(User::new);
        userRepository.deleteChildUserFromParentChildTable(firstChildUser.getId());
        userRepository.deleteChildUserFromUserTable(firstChildUser.getId());
        Assertions.assertFalse(userRepository.existsById(firstChildUser.getId()));
    }

    /*todo: delete specified Ids when database preserves users initially */
    @Test
    @Order(23)
    @DisplayName("Delete All Users")
    public void deleteAll() {
        userRepository.deleteAll();
    }
    /*:::::::::  Testing: Deletion Ends :::::::::::::::::::::*/


    @AfterAll
    @DisplayName("Tear Down All")
    public static void tearDownAll() {
        log.info("**** Repository layer test ends  *****");
    }
}

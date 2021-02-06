package com.shetu.user_management_app;

import com.shetu.user_management_app.dto.request.UserRequestDTO;
import com.shetu.user_management_app.enums.UserType;
import com.shetu.user_management_app.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UnitTestUtils {

    public static UserRequestDTO createUserRequestDTOAlignedWithFirstParent(){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setFirstName("Belal");
        userRequestDTO.setLastName("Hossen");
        userRequestDTO.setStreet("Mirpur");
        userRequestDTO.setCity("Dhaka");
        userRequestDTO.setState("Bangladesh");
        userRequestDTO.setZipCode("1216");
        userRequestDTO.setUserType(UserType.PARENT.name());
//        userRequestDTO.setChildUsers();
//        userRequestDTO.setParentUserId();
        return userRequestDTO;
    }
    public static UserRequestDTO createUserRequestDTOAlignedWithFirstParentWithoutFirstName(){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setLastName("Hossen");
        userRequestDTO.setStreet("Mirpur");
        userRequestDTO.setCity("Dhaka");
        userRequestDTO.setState("Bangladesh");
        userRequestDTO.setZipCode("1216");
        userRequestDTO.setUserType(UserType.PARENT.name());
//        userRequestDTO.setChildUsers();
//        userRequestDTO.setParentUserId();
        return userRequestDTO;
    }

    public static UserRequestDTO createChildUserRequestDTOWithoutParentUserId(){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setFirstName("Latu");
        userRequestDTO.setLastName("Hossen");
        userRequestDTO.setStreet("Mirpur");
        userRequestDTO.setCity("Dhaka");
        userRequestDTO.setState("Bangladesh");
        userRequestDTO.setZipCode("1216");
        userRequestDTO.setUserType(UserType.CHILD.name());
        return userRequestDTO;
    }

    public static User createFirstParentUserWithoutChildUsers(){
        User user = new User();
        user.setId(1);
        user.setFirstName("Belal");
        user.setLastName("Hossen");
        user.setStreet("Mirpur");
        user.setCity("Dhaka");
        user.setState("Bangladesh");
        user.setZipCode("1216");
        user.setUserType(UserType.PARENT);
//        user.setChildUsers();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static User createdFirstParentUserWithoutChildUsersFromDB(){
        User user = new User();
        user.setId(200);
        user.setFirstName("Belal");
        user.setLastName("Hossen");
        user.setStreet("Mirpur");
        user.setCity("Dhaka");
        user.setState("Bangladesh");
        user.setZipCode("1216");
        user.setUserType(UserType.PARENT);
        user.setChildUsers(new ArrayList<>());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static User createdThirdParentUserWithoutChildUsersFromDB(){
        User user = new User();
        user.setId(10);
        user.setFirstName("Shahi");
        user.setLastName("Hossen");
        user.setStreet("Mirpur");
        user.setCity("Dhaka");
        user.setState("Bangladesh");
        user.setZipCode("1216");
        user.setUserType(UserType.PARENT);
        user.setChildUsers(new ArrayList<>());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static User createSecondParentUserWithChildUsers(){
        User user = new User();
        user.setId(2);
        user.setFirstName("Shahariar");
        user.setLastName("Hossen");
        user.setStreet("Mirpur");
        user.setCity("Dhaka");
        user.setState("Bangladesh");
        user.setZipCode("1216");
        user.setUserType(UserType.PARENT);
        user.setChildUsers(preparedUserListForSecondParent());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static List<User> preparedUserListForSecondParent(){
        User childUser1 = createFirstChildUserAttachedToSecondParentUser();
        List<User> userListForSecondParent = new ArrayList<>();
        userListForSecondParent.add(childUser1);
        return userListForSecondParent;
    }
    public static User createFirstChildUserAttachedToSecondParentUser(){
        User user = new User();
        user.setId(3);
        user.setFirstName("Mehnaz");
        user.setLastName("Chowdhury");
        user.setStreet("Mirpur");
        user.setCity("Dhaka");
        user.setState("Bangladesh");
        user.setZipCode("1216");
        user.setUserType(UserType.PARENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static User createSecondChildUserAttachedToSecondParentUser(){
        User user = new User();
        user.setId(4);
        user.setFirstName("Khadiza");
        user.setLastName("Khatun");
        user.setStreet("Mirpur");
        user.setCity("Dhaka");
        user.setState("Bangladesh");
        user.setZipCode("1216");
        user.setUserType(UserType.PARENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static User createThirdChildUserWillBeAttachedToFirstParentUser(){
        User user = new User();
        user.setId(5);
        user.setFirstName("Mehnaz");
        user.setLastName("Chowdhury");
        user.setStreet("Mirpur");
        user.setCity("Dhaka");
        user.setState("Bangladesh");
        user.setZipCode("1216");
        user.setUserType(UserType.PARENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }
}

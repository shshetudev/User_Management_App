package com.shetu.user_management_app.dto.request;

import com.shetu.user_management_app.enums.ErrorCodes;
import com.shetu.user_management_app.enums.UserType;
import com.shetu.user_management_app.model.User;
import com.shetu.user_management_app.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserRequestDTO {
    @Size(min = 1)
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    @Size(min = 1)
    private String userType;
    private List<User> childUsers = new ArrayList<>();
    /*todo: Modify in front end*/
    private Integer parentUserId;
}

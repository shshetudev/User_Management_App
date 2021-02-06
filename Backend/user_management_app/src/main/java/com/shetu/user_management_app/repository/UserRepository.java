package com.shetu.user_management_app.repository;

import com.shetu.user_management_app.enums.UserType;
import com.shetu.user_management_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByFirstNameAndUserType(String firstName, UserType userType);

    List<User> findAllByUserType(UserType userType);

    Optional<User> findByIdAndUserType(Integer id, UserType userType);

    String DELETE_FROM_PARENT_CHILD_TABLE_BY_ID = "delete from `parent_child` where child_id = :childId";

    @Modifying
    @Transactional
    @Query(value = DELETE_FROM_PARENT_CHILD_TABLE_BY_ID, nativeQuery = true)
    void deleteChildUserFromParentChildTable(@Param("childId") Integer childId);

    String DELETE_FROM_USER_TABLE_BY_ID = "delete from `user` where id = :id";

    @Modifying
    @Transactional
    @Query(value = DELETE_FROM_USER_TABLE_BY_ID, nativeQuery = true)
    void deleteChildUserFromUserTable(@Param("id") Integer id);
}

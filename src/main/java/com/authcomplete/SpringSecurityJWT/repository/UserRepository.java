package com.authcomplete.SpringSecurityJWT.repository;


import com.authcomplete.SpringSecurityJWT.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);

    @Query(value = "SELECT r.name \n" +
            "FROM users u\n" +
            "JOIN user_role ur ON u.id = ur.user_id\n" +
            "JOIN roles r ON ur.role_id = r.id\n" +
            "WHERE u.username = :username;", nativeQuery = true)
    Set<String> findRolesByUsername(String username);
}


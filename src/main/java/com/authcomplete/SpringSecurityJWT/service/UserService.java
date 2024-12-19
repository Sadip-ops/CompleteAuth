package com.authcomplete.SpringSecurityJWT.service;

import com.authcomplete.SpringSecurityJWT.dto.requestDTO.RoleRequest;
import com.authcomplete.SpringSecurityJWT.dto.requestDTO.UserRequest;
import com.authcomplete.SpringSecurityJWT.dto.responseDTO.UserResponse;
import com.authcomplete.SpringSecurityJWT.entity.UserRole;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserResponse saveUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    void deleteUserById(Long id);

    List<UserResponse> getAllUser();

    void deleteRoleById(Long roleId);

    UserRole addRole(RoleRequest roleRequest);

}

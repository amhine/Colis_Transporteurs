package com.example.colis.service;

import com.example.colis.dto.PageResponse;
import com.example.colis.dto.user.CreateUserRequest;
import com.example.colis.dto.user.UpdateUserRequest;
import com.example.colis.dto.user.UserResponse;

public interface UserService {

    PageResponse<UserResponse> getAllUsers(int page, int size);
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(String id, UpdateUserRequest request);
    void deleteUser(String id);
    UserResponse activateUser(String id);
    UserResponse deactivateUser(String id);
}

package com.zezame.timasi.service;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.user.ChangePasswordRequestDTO;
import com.zezame.timasi.dto.user.UpdateUserRequestDTO;
import com.zezame.timasi.dto.user.UserRegisterRequestDTO;
import com.zezame.timasi.dto.user.UserResponseDTO;
import com.zezame.timasi.model.company.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    List<UserResponseDTO> getUsers(String roleCode);

    UserResponseDTO getUser(String id);

    CreateResponseDTO createUser(UserRegisterRequestDTO request);

    UpdateResponseDTO updateUser(String id, UpdateUserRequestDTO request);

    CommonResponseDTO deleteUser(String id);

    UpdateResponseDTO changePassword(ChangePasswordRequestDTO request);
}

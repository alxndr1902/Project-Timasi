package com.zezame.timasi.service.impl;

import com.zezame.timasi.constant.Message;
import com.zezame.timasi.constant.RoleCode;
import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.user.ChangePasswordRequestDTO;
import com.zezame.timasi.dto.user.UpdateUserRequestDTO;
import com.zezame.timasi.dto.user.UserRegisterRequestDTO;
import com.zezame.timasi.dto.user.UserResponseDTO;
import com.zezame.timasi.exceptiohandler.exception.DataIntegrationException;
import com.zezame.timasi.exceptiohandler.exception.DuplicateException;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
import com.zezame.timasi.model.company.Company;
import com.zezame.timasi.model.company.Role;
import com.zezame.timasi.model.company.User;
import com.zezame.timasi.repository.CompanyRepository;
import com.zezame.timasi.repository.RoleRepository;
import com.zezame.timasi.repository.UserRepository;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends BaseService implements UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, CompanyRepository companyRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByEmail(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        return user;
    }

    @Override
    public List<UserResponseDTO> getUsers(String roleCode) {
        List<User> users = userRepository.findAllByRoleCode(roleCode);
        List<UserResponseDTO> responses = users.stream()
                .map(this::mapToDto)
                .toList();
        return responses;
    }

    @Override
    public UserResponseDTO getUser(String id) {
        User user = findUserById(id);
        UserResponseDTO response = mapToDto(user);
        return response;
    }

    private UserResponseDTO mapToDto(User user) {
        UserResponseDTO dto = new UserResponseDTO(
                user.getId(), user.getFullName(), user.getEmail(),
                user.getPhoneNumber(), user.getRole().getName(),
                user.getVersion());

        return dto;
    }

    @Override
    public CreateResponseDTO createUser(UserRegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email Is Not Available");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicateException("Phone Number Is Not Available");
        }

        if (userRepository.existsByIdentificationNumber(request.getIdentificationNumber())) {
            throw new DuplicateException("Identification Number Is Not Available");
        }

        UUID roleId = convertToUUID(request.getRoleId());
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role Not Found"));

        UUID companyId = convertToUUID(request.getCompanyId());
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company Not Found"));

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setIdentificationNumber(request.getIdentificationNumber());
        user.setRole(role);
        user.setCompany(company);
        User savedUser = userRepository.save(prepareCreate(user));
        return new CreateResponseDTO(savedUser.getId(), Message.CREATED.getName());
    }

    @Override
    public UpdateResponseDTO updateUser(String id, UpdateUserRequestDTO request) {
        User user = findUserById(id);

        if (!user.getVersion().equals(request.getVersion())) {
            throw new DataIntegrationException("Error Updating User, Please Refresh The Page");
        }

        if (!user.getEmail().equals(request.getEmail())) {
            userRepository.findByEmail(request.getEmail())
                    .ifPresent(u -> {
                        throw new DuplicateException("Email Is Not Available");
                    });
        }
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        User updatedUser = userRepository.save(prepareUpdate(user));
        return new UpdateResponseDTO(updatedUser.getId(), Message.UPDATED.getName(), updatedUser.getVersion());
    }

    @Override
    public CommonResponseDTO deleteUser(String id) {
        User user = findUserById(id);
        userRepository.delete(user);
        return new CommonResponseDTO(Message.DELETED.getName());
    }

    @Override
    public UpdateResponseDTO changePassword(ChangePasswordRequestDTO request) {
        User user = findUserById(principalService.getPrincipal().getId());
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new DataIntegrationException("Old Password Do Not Match");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        User updatedUser = userRepository.save(prepareUpdate(user));
        return new UpdateResponseDTO(updatedUser.getId(), Message.UPDATED.getName(), updatedUser.getVersion());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return new org.springframework.security.core.userdetails.User(
                email, user.getPassword(), new ArrayList<>());
    }

    private User findUserById(String id) {
        UUID userId = convertToUUID(id);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
        return user;
    }
}

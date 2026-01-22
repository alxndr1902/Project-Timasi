package com.zezame.timasi.service.impl;

import com.zezame.timasi.constant.Message;
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
        List<UserResponseDTO> dtos = users.stream()
                .map(this::mapToDto)
                .toList();
        return dtos;
    }

    @Override
    public UserResponseDTO getUser(String id) {
        var user = findUserById(id);
        var dto = mapToDto(user);
        return dto;
    }

    private UserResponseDTO mapToDto(User user) {
        var dto = new UserResponseDTO(
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

        var roleId = convertToUUID(request.getRoleId());
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role Not Found"));

        var companyId = convertToUUID(request.getCompanyId());
        var company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company Not Found"));

        var user = new User();
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
        var user = findUserById(id);

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

        var updatedUser = userRepository.save(prepareUpdate(user));
        return new UpdateResponseDTO(updatedUser.getId(), Message.UPDATED.getName(), updatedUser.getVersion());
    }

    @Override
    public CommonResponseDTO deleteUser(String id) {
        var user = findUserById(id);
        userRepository.delete(user);
        return new CommonResponseDTO(Message.DELETED.getName());
    }

    @Override
    public UpdateResponseDTO changePassword(ChangePasswordRequestDTO request) {
        var user = findUserById(principalService.getPrincipal().getId());
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new DataIntegrationException("Old Password Do Not Match");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        var updatedUser = userRepository.save(prepareUpdate(user));
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
        var userId = convertToUUID(id);
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
        return user;
    }
}

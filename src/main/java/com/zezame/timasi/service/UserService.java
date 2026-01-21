package com.zezame.timasi.service;

import com.zezame.timasi.model.company.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);
}

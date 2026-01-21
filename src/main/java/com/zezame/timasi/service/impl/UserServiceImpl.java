package com.zezame.timasi.service.impl;

import com.zezame.timasi.model.company.User;
import com.zezame.timasi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User findByEmail(String email) {
        return null;
    }
}

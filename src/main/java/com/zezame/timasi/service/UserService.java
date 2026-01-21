package com.zezame.timasi.service;

import com.zezame.timasi.model.company.User;

public interface UserService {
    User findByEmail(String email);
}

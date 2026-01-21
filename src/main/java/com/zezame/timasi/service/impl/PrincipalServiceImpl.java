package com.zezame.timasi.service.impl;

import com.zezame.timasi.pojo.AuthorizationPojo;
import com.zezame.timasi.service.PrincipalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalServiceImpl implements PrincipalService {
    @Override
    public AuthorizationPojo getPrincipal() {
        var auth  = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new UsernameNotFoundException("Invalid Login");
        }
        return (AuthorizationPojo) auth.getPrincipal();
    }
}

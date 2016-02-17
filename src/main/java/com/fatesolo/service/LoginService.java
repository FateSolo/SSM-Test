package com.fatesolo.service;

import com.fatesolo.mapper.UserMapper;
import com.fatesolo.model.User;
import com.fatesolo.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService {

    @Resource
    private UserMapper mapper;

    public boolean login(String username, String password) {
        User user = mapper.getUser(username);

        return user != null && MD5Util.validate(password, user.getPassword());
    }

}

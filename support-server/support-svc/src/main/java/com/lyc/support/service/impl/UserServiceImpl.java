package com.lyc.support.service.impl;

import com.lyc.support.entity.User;
import com.lyc.support.repository.UserRepository;
import com.lyc.support.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/2/22 8:54
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUserInfo() {
        return userRepository.findAll();
    }
}

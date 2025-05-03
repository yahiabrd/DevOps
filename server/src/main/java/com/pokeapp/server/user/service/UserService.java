package com.pokeapp.server.user.service;

import com.pokeapp.server.user.dao.UserDao;
import com.pokeapp.server.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<User> getUsers() {
        return userDao.fetchUsers().stream()
                .map(userMap -> new User(
                        (Integer) userMap.get("id"),
                        (String) userMap.get("username"),
                        (String) userMap.get("email")
                ))
                .collect(Collectors.toList());
    }
}

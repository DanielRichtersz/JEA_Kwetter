package com.github.danielrichtersz.dao;

import com.github.danielrichtersz.entity.User;

public interface UserDAO extends BaseDAO<User> {

    User getByCredentials(String email, String password);

}

package com.ecommerce.domain.repository;

import com.ecommerce.domain.User;

import java.util.List;

public interface IUserRepository {
    List<User> list();

    User get(long id);

    User get(String email);

    User getUserId(final String name);

    long create(User user);

    int update(User user);

    int delete(long id);
}

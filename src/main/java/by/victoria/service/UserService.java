package by.victoria.service;

import by.victoria.model.entity.User;

public interface UserService {
    void create(User user);

    User getAuthenticated();

    void save(User user);
}

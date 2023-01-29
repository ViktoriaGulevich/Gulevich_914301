package by.victoria.service;

import by.victoria.model.entity.User;

public interface UserSecurityService {
    User encodePassword(User user);

    User getAuthenticatedUser();
}

package by.victoria.service.impl;

import by.victoria.exception.NotFoundException;
import by.victoria.model.entity.User;
import by.victoria.repository.UserRepository;
import by.victoria.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public User encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        return user;
    }

    @Override
    public User getAuthenticatedUser() {
        String login = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByLogin(login).orElseThrow(() ->
                new NotFoundException("Can't find user by current email: " + login)
        );
    }
}

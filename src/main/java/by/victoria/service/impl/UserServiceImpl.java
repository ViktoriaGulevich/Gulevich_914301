package by.victoria.service.impl;

import by.victoria.exception.DuplicateException;
import by.victoria.exception.NotFoundException;
import by.victoria.model.entity.Role;
import by.victoria.model.entity.User;
import by.victoria.repository.RoleRepository;
import by.victoria.repository.UserRepository;
import by.victoria.service.UserSecurityService;
import by.victoria.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserSecurityService userSecurityService;


    @Override
    public void create(User user, boolean isRecruiter) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new DuplicateException("User with login " + user.getLogin() + " already exists");
        }
//        if (userRepository.existsByEmail(user.getEmail())) {
//            throw new DuplicateException("User with email " + user.getEmail() + " already exists");
//        }
        Role role;
        if (isRecruiter) {
            role = roleRepository.findByName("RECRUITER").orElseThrow(() ->
                    new NotFoundException("Can't find role with name = RECRUITER")
            );
        } else {
            role = roleRepository.findByName("USER").orElseThrow(() ->
                    new NotFoundException("Can't find role with name = USER")
            );
        }
        user = userSecurityService.encodePassword(user);
        user.addRole(role);

        userRepository.save(user);
    }

    @Override
    public User getAuthenticated() {
        return userSecurityService.getAuthenticatedUser();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByResumeId(Long resumeId) {
        return userRepository.findAllUserById(resumeId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Нет резюме с id = " + resumeId));
    }
}

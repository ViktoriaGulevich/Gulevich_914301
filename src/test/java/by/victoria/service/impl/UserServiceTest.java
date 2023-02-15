package by.victoria.service.impl;

import by.victoria.data.UserData;
import by.victoria.exception.DuplicateException;
import by.victoria.exception.NotFoundException;
import by.victoria.model.entity.Role;
import by.victoria.model.entity.User;
import by.victoria.repository.RoleRepository;
import by.victoria.repository.UserRepository;
import by.victoria.service.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final User user = UserData.getUserEntity();

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserSecurityService userSecurityService;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void create_saveCorrect() {
        Role role = new Role();
        String roleName = "USER";
        role.setName(roleName);

        when(userRepository.existsByLogin(user.getLogin())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));
        when(userSecurityService.encodePassword(user)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.create(user);

        verify(userRepository).existsByLogin(user.getLogin());
        verify(userRepository).existsByEmail(user.getEmail());
        verify(roleRepository).findByName(roleName);
        verify(userSecurityService).encodePassword(user);
        verify(userRepository).save(user);
    }

    @Test
    void create_assertThrowByLogin() {
        when(userRepository.existsByLogin(user.getLogin())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> userService.create(user));
    }

    @Test
    void create_assertThrowByEmail() {
        when(userRepository.existsByLogin(user.getLogin())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> userService.create(user));
    }

    @Test
    void create_assertThrowByRole() {
        String roleName = "USER";

        when(userRepository.existsByLogin(user.getLogin())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.create(user));
    }

    @Test
    void getAuthenticated() {
        when(userSecurityService.getAuthenticatedUser()).thenReturn(user);

        User actual = userService.getAuthenticated();

        assertEquals(user, actual);
        verify(userSecurityService).getAuthenticatedUser();
    }

    @Test
    void save() {
        when(userRepository.save(user)).thenReturn(user);

        userService.save(user);

        verify(userRepository).save(user);
    }
}

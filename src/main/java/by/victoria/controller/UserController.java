package by.victoria.controller;

import by.victoria.mapper.UserMapper;
import by.victoria.model.dto.UserDto;
import by.victoria.model.entity.User;
import by.victoria.service.EmailService;
import by.victoria.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    private final UserMapper userMapper;


    @GetMapping("/login")
    public Principal login(Principal user) {
        return user;
    }

    @GetMapping
    public UserDto getCurrent() {
        User user = userService.getAuthenticated();

        return userMapper.toUserDto(user);
    }

    @PostMapping
    public void create(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.toUser(userDto);

        emailService.send(user);
    }

    @PostMapping("/{code}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserDto userDto, @PathVariable String code) {
        User user = userMapper.toUser(userDto);

        if (emailService.checkCode(user, code)) {
            userService.create(user, userDto.isRecruiter());
        } else {
            throw new RuntimeException("Неверный код");
        }
    }
}

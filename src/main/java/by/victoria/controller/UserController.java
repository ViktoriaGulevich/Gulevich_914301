package by.victoria.controller;

import by.victoria.mapper.UserMapper;
import by.victoria.model.dto.UserDto;
import by.victoria.model.entity.User;
import by.victoria.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;


    @GetMapping("/login")
    public Integer login() {
        return 1;
    }

    @GetMapping
    public UserDto getCurrent() {
        User user = userService.getAuthenticated();

        return userMapper.toUserDto(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.toUser(userDto);

        userService.create(user);
    }
}

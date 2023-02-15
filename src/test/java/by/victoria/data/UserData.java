package by.victoria.data;

import by.victoria.model.dto.UserDto;
import by.victoria.model.entity.User;

import java.util.HashSet;

public class UserData {
    private static final String ANY_STRING = "any string";
    private static final String ANY_EMAIL = "any.email@mail.com";

    public static User getUserEntity() {
        User user = new User();

        user.setLogin(ANY_STRING);
        user.setEmail(ANY_EMAIL);
        user.setPassword(ANY_STRING);
        user.setResumes(new HashSet<>());
        user.setVacancies(new HashSet<>());

        return user;
    }

    public static UserDto getUserDto() {
        UserDto userDto = new UserDto();

        userDto.setLogin(ANY_STRING);
        userDto.setEmail(ANY_EMAIL);
        userDto.setPassword(ANY_STRING);

        return userDto;
    }
}

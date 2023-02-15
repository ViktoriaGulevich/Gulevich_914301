package by.victoria.controller;

import by.victoria.data.UserData;
import by.victoria.mapper.UserMapper;
import by.victoria.model.dto.UserDto;
import by.victoria.model.entity.User;
import by.victoria.service.UserService;
import by.victoria.util.ObjectMapperWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private final User user = UserData.getUserEntity();
    private final UserDto userDto = UserData.getUserDto();

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void getCurrent() {
        when(userService.getAuthenticated()).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        MvcResult result = mockMvc.perform(get("/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserDto actual = ObjectMapperWrapper.toObject(objectMapper, result, new TypeReference<>() {
        });

        assertEquals(userDto, actual);
    }

    @Test
    @SneakyThrows
    void create() {
        when(userMapper.toUser(userDto)).thenReturn(user);
        doNothing().when(userService).create(user);

        mockMvc.perform(post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());

        verify(userMapper).toUser(userDto);
        verify(userService).create(user);
    }
}

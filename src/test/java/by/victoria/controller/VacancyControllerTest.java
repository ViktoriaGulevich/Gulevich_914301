package by.victoria.controller;

import by.victoria.data.VacancyData;
import by.victoria.mapper.VacancyMapper;
import by.victoria.model.dto.VacancyDto;
import by.victoria.model.entity.Vacancy;
import by.victoria.service.VacancyService;
import by.victoria.util.ObjectMapperWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class VacancyControllerTest {

    private static final Long ID = 1L;
    private static final String URL = "/vacancy";
    private static final String URL_ID = URL + "/{id}";

    private final Vacancy vacancy = VacancyData.getVacancy();
    private final VacancyDto vacancyDto = VacancyData.getVacancyDto();
    private final List<Vacancy> vacancyList = VacancyData.getVacancyList();
    private final List<VacancyDto> vacancyDtoList = VacancyData.getVacancyDtoList();

    @Mock
    private VacancyService vacancyService;
    @Mock
    private VacancyMapper vacancyMapper;
    @InjectMocks
    private VacancyController vacancyController;

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(vacancyController)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build();
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "USER")
    void findAll() {
        when(vacancyService.findAll()).thenReturn(vacancyList);
        when(vacancyMapper.toVacancyDtoList(vacancyList)).thenReturn(vacancyDtoList);

        MvcResult mvcResult = mockMvc.perform(get(URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<VacancyDto> actual = ObjectMapperWrapper.toObject(objectMapper, mvcResult, new TypeReference<>() {
        });

        assertEquals(vacancyDtoList, actual);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void find() {
        when(vacancyService.find(ID)).thenReturn(vacancy);
        when(vacancyMapper.toVacancyDto(vacancy)).thenReturn(vacancyDto);

        MvcResult mvcResult = mockMvc.perform(get(URL_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        VacancyDto actual = ObjectMapperWrapper.toObject(objectMapper, mvcResult, new TypeReference<>() {
        });

        assertEquals(vacancyDto, actual);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void add() {
        when(vacancyMapper.toVacancy(vacancyDto)).thenReturn(vacancy);
        doNothing().when(vacancyService).add(vacancy);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyDto)))
                .andExpect(status().isCreated());

        verify(vacancyMapper).toVacancy(vacancyDto);
        verify(vacancyService).add(vacancy);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void update() {
        when(vacancyMapper.toVacancy(vacancyDto)).thenReturn(vacancy);
        doNothing().when(vacancyService).update(vacancy);

        mockMvc.perform(patch(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyDto)))
                .andExpect(status().isResetContent());

        verify(vacancyMapper).toVacancy(vacancyDto);
        verify(vacancyService).update(vacancy);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void deleteTest() {
        doNothing().when(vacancyService).delete(ID);

        mockMvc.perform(delete(URL_ID, ID))
                .andExpect(status().isOk());

        verify(vacancyService).delete(ID);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "USER")
    void respond() {
        doNothing().when(vacancyService).respond(ID);

        mockMvc.perform(post(URL + "/respond")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(ID)))
                .andExpect(status().isOk());

        verify(vacancyService).respond(ID);
    }
}

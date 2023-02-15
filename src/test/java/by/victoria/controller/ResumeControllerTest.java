package by.victoria.controller;

import by.victoria.data.ResumeData;
import by.victoria.mapper.FilterMapper;
import by.victoria.mapper.ResumeMapper;
import by.victoria.model.dto.FilterDto;
import by.victoria.model.dto.ResumeDto;
import by.victoria.model.entity.Resume;
import by.victoria.service.EmailService;
import by.victoria.service.ReportService;
import by.victoria.service.ResumeService;
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
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ResumeControllerTest {

    private static final Long ID = 1L;
    private static final String URL = "/resume";
    private static final String URL_FAVORITES = URL + "/favorites";
    private static final String URL_TO_SEND = URL + "/to-send";
    private static final String URL_ID = URL + "/{id}";

    private final Resume resume = ResumeData.getResume();
    private final ResumeDto resumeDto = ResumeData.getResumeDto();
    private final List<Resume> resumeList = ResumeData.getResumeList();
    private final List<ResumeDto> resumeDtoList = ResumeData.getResumeDtoList();

    @Mock
    private ResumeService resumeService;
    @Mock
    private EmailService emailService;
    @Mock
    private ReportService reportService;
    @Mock
    private FilterMapper filterMapper;
    @Mock
    private ResumeMapper resumeMapper;
    @InjectMocks
    private ResumeController resumeController;

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(resumeController)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build();
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void findAll() {
        when(resumeService.findAll()).thenReturn(resumeList);
        when(resumeMapper.toResumeDtoList(resumeList)).thenReturn(resumeDtoList);

        MvcResult mvcResult = mockMvc.perform(get(URL + "/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<ResumeDto> actual = ObjectMapperWrapper.toObject(objectMapper, mvcResult, new TypeReference<>() {
        });

        assertEquals(resumeDtoList, actual);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void findAllByFilter() {
        FilterDto filterDto = new FilterDto();
        Predicate<Resume> resumeFilter = resume -> true;

        when(filterMapper.toFilter(filterDto)).thenReturn(resumeFilter);
        when(resumeService.findAll(resumeFilter, null)).thenReturn(resumeList);
        when(resumeMapper.toResumeDtoList(resumeList)).thenReturn(resumeDtoList);

        MvcResult mvcResult = mockMvc.perform(post(URL + "/filter")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filterDto)))
                .andExpect(status().isOk())
                .andReturn();
        List<ResumeDto> actual = ObjectMapperWrapper.toObject(objectMapper, mvcResult, new TypeReference<>() {
        });

        assertEquals(resumeDtoList, actual);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void findFavorites() {
        when(resumeService.findFavorites()).thenReturn(resumeList);
        when(resumeMapper.toResumeDtoList(resumeList)).thenReturn(resumeDtoList);

        MvcResult mvcResult = mockMvc.perform(get(URL_FAVORITES)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<ResumeDto> actual = ObjectMapperWrapper.toObject(objectMapper, mvcResult, new TypeReference<>() {
        });

        assertEquals(resumeDtoList, actual);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void addToFavorites() {
        doNothing().when(resumeService).addToFavorites(ID);

        mockMvc.perform(post(URL_FAVORITES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(ID)))
                .andExpect(status().isOk());

        verify(resumeService).addToFavorites(ID);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void deleteFromFavorites() {
        doNothing().when(resumeService).deleteFromFavorites(ID);

        mockMvc.perform(patch(URL_FAVORITES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(ID)))
                .andExpect(status().isOk());

        verify(resumeService).deleteFromFavorites(ID);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void findToSend() {
        when(resumeService.findToSend()).thenReturn(resumeList);
        when(resumeMapper.toResumeDtoList(resumeList)).thenReturn(resumeDtoList);

        MvcResult mvcResult = mockMvc.perform(get(URL_TO_SEND)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<ResumeDto> actual = ObjectMapperWrapper.toObject(objectMapper, mvcResult, new TypeReference<>() {
        });

        assertEquals(resumeDtoList, actual);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void addToSend() {
        doNothing().when(resumeService).addToSend(ID);

        mockMvc.perform(post(URL_TO_SEND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(ID)))
                .andExpect(status().isOk());

        verify(resumeService).addToSend(ID);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void deleteFromSend() {
        doNothing().when(resumeService).deleteFromSend(ID);

        mockMvc.perform(patch(URL_TO_SEND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(ID)))
                .andExpect(status().isOk());

        verify(resumeService).deleteFromSend(ID);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void find() {
        when(resumeService.find(ID)).thenReturn(resume);
        when(resumeMapper.toResumeDto(resume)).thenReturn(resumeDto);

        MvcResult mvcResult = mockMvc.perform(get(URL_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ResumeDto actual = ObjectMapperWrapper.toObject(objectMapper, mvcResult, new TypeReference<>() {
        });

        assertEquals(resumeDto, actual);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "USER")
    void add() {
        when(resumeMapper.toResume(resumeDto)).thenReturn(resume);
        doNothing().when(resumeService).add(resume);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resumeDto)))
                .andExpect(status().isCreated());

        verify(resumeMapper).toResume(resumeDto);
        verify(resumeService).add(resume);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "USER")
    void update() {
        when(resumeMapper.toResume(resumeDto)).thenReturn(resume);
        doNothing().when(resumeService).update(resume);

        mockMvc.perform(patch(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resumeDto)))
                .andExpect(status().isResetContent());

        verify(resumeMapper).toResume(resumeDto);
        verify(resumeService).update(resume);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "USER")
    void deleteTest() {
        doNothing().when(resumeService).delete(ID);

        mockMvc.perform(delete(URL_ID, ID))
                .andExpect(status().isOk());

        verify(resumeService).delete(ID);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "RECRUITER")
    void send() {
        String description = "message";

        when(resumeService.findToSend()).thenReturn(resumeList);
        doNothing().when(emailService).send(resumeList, description);
        doNothing().when(reportService).saveAll(resumeList, description);
        doNothing().when(resumeService).deleteAllFromSend();

        mockMvc.perform(post(URL + "/send")
                        .content(description))
                .andExpect(status().isOk());

        verify(resumeService).findToSend();
        verify(emailService).send(resumeList, description);
        verify(reportService).saveAll(resumeList, description);
        verify(resumeService).deleteAllFromSend();
    }
}

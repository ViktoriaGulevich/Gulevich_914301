package by.victoria.controller;

import by.victoria.mapper.ReportMapper;
import by.victoria.model.dto.ReportDto;
import by.victoria.model.entity.Report;
import by.victoria.service.ReportService;
import by.victoria.util.ObjectMapperWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerTest {

    @MockBean
    private ReportService reportService;
    @MockBean
    private ReportMapper reportMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    @WithUserDetails(value = "RECRUITER")
    void findAll() {
        List<Report> reportList = List.of(new Report());
        List<ReportDto> reportDtoList = List.of(new ReportDto());

        when(reportService.findAll()).thenReturn(reportList);
        when(reportMapper.toListDto(reportList)).thenReturn(reportDtoList);

        MvcResult result = mockMvc.perform(get("/report"))
                .andExpect(status().isOk())
                .andReturn();
        List<ReportDto> actual = ObjectMapperWrapper.toObject(objectMapper, result, new TypeReference<>() {
        });

        assertEquals(reportDtoList, actual);
    }
}

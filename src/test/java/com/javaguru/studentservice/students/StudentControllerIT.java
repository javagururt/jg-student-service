package com.javaguru.studentservice.students;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
//Var 1
@Sql("/scripts/truncate_students_table.sql")
//В случае использования H2 базы. Поднимает контекст перед каждым тестовым методом
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository repository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void shouldCreateStudentWithQuote() throws Exception {
        StudentQuoteDto quoteResponse = new StudentQuoteDto();
        quoteResponse.setId("5a82224c89919d0004b357ce");
        quoteResponse.setAuthor("Test Author");
        quoteResponse.setQuote("Test Quote");

        when(restTemplate.getForObject("https://programming-quotes-api.herokuapp.com/quotes/random", StudentQuoteDto.class))
                .thenReturn(quoteResponse);

        mockMvc.perform(post("/api/v1/students")
                .content(createStudentJson())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/v1/students/1")));
    }

    //Var 2
    @Test
    public void shouldFindStudentById() throws Exception {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName("TEST - 1");
        studentEntity.setQuote("Test Quote - 1");
        repository.save(studentEntity);

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("TEST - 1"))
                .andExpect(jsonPath("$.quote").value("Test Quote - 1"));
    }

    private String createStudentJson() throws JSONException {
        return new JSONObject()
                .put("name", "TEST")
                .toString();
    }
}
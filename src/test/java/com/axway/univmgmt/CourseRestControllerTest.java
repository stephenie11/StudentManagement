package com.axway.univmgmt;

import com.axway.univmgmt.service.CourseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class CourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseServiceMock;

    @Test
    public void accessProtected() throws Exception {
        mockMvc.perform(get("/api/courses/view"))
                .andExpect(unauthenticated())
                .andExpect(status().isUnauthorized());
    }
}

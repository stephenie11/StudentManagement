package com.axway.univmgmt;

import com.axway.univmgmt.entity.Course;
import com.axway.univmgmt.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class CourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestHelper testHelper;

    private Course course;
    private Student student;

    private ObjectMapper mapper;

    @Before
    public void initTest() {
        testHelper.cleanDataBase();
        student = testHelper.createStudent();
        course = testHelper.createCourse();
        mapper = new ObjectMapper();
    }

    @Test
    public void testBasicInMemoryAuthentication() throws Exception {
        mockMvc.perform(get("/api/courses/view"))
                .andExpect(unauthenticated())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAddCourse() throws Exception {
        String dummyCourseTitle = "Ingineria programarii";

        mockMvc.perform(post("/api/courses?courseTitle=" + dummyCourseTitle)
                .with(httpBasic("stefania", "stefania"))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("course_title").value(dummyCourseTitle))
                .andExpect(jsonPath("course_id").isNumber());
    }

    @Test
    public void testUpdateCourse() throws Exception {
        Set<Student> students = new HashSet<>();
        students.add(student);
        course.setStudents(students);
        String jsonCourse = mapper.writeValueAsString(course);

        mockMvc.perform(patch("/api/courses/" + course.getId())
                .with(httpBasic("stefania", "stefania"))
                .content(jsonCourse)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.students[0].student_id").value(student.getId()));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/api/courses?courseId=" + course.getId())
                .with(httpBasic("stefania", "stefania"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());
    }

    @Test
    public void testDeleteCourseThatDoesNotExist() throws Exception {
        long courseIdThatDoesNotExist = 0;
        mockMvc.perform(delete("/api/courses?courseId=" + courseIdThatDoesNotExist)
                .with(httpBasic("stefania", "stefania"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isNoContent());
    }
}

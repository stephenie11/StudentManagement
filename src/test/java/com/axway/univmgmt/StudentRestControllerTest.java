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
public class StudentRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestHelper testHelper;

	private Student student;
	private Course course;

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
		mockMvc.perform(get("/api/students/view"))
				.andExpect(unauthenticated())
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void testAddStudent() throws Exception {
		String jsonStudent = mapper.writeValueAsString(student);

		mockMvc.perform(post("/api/students")
				.with(httpBasic("stefania", "stefania"))
				.content(jsonStudent)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
		).andExpect(status().isCreated())
		.andExpect(jsonPath("email").value("pirvu.alexandra@gmail.com"))
		.andExpect(jsonPath("student_id").isNumber());
	}

	@Test
	public void testUpdateStudent() throws Exception {
		String updateFirstName = "StefaniaUpdated";
		student.setFirstName(updateFirstName);
		Set<Course> courses = new HashSet<>();
		courses.add(course);
		student.setCourses(courses);
		String jsonStudent = mapper.writeValueAsString(student);

		mockMvc.perform(patch("/api/students/" + student.getId())
				.with(httpBasic("stefania", "stefania"))
				.content(jsonStudent)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
		).andExpect(status().isOk())
				.andExpect(jsonPath("first_name").value(updateFirstName))
				.andExpect(jsonPath("$.courses[0].course_id").value(course.getId()));
	}

	@Test
	public void testDeleteStudent() throws Exception {
		mockMvc.perform(delete("/api/students?studentId=" + student.getId())
				.with(httpBasic("stefania", "stefania"))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
		).andExpect(status().isOk());
	}

	@Test
	public void testDeleteStudentThatDoesNotExist() throws Exception {
		long studentIdThatDoesNotExist = 0;
		mockMvc.perform(delete("/api/students?studentId=" + studentIdThatDoesNotExist)
				.with(httpBasic("stefania", "stefania"))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
		).andExpect(status().isNoContent());
	}
}

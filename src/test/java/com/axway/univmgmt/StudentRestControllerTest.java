package com.axway.univmgmt;

import com.axway.univmgmt.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
	private StudentService studentServiceMock;

	@Test
	public void accessProtected() throws Exception {
		mockMvc.perform(get("/api/students/view"))
				.andExpect(unauthenticated())
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void testAddStudent() throws Exception {

		String jsonStudent = "{\n" +
				"  \"cnp\": \"2970121035286\",\n" +
				"  \"college_year\": 3,\n" +
				"  \"courses\": [],\n" +
				"  \"email\": \"pirvu.stefania@gmail.com\",\n" +
				"  \"first_name\": \"Stefania\",\n" +
				"  \"last_name\": \"Pirvu\",\n" +
				"  \"phone_number\": \"0739042106\",\n" +
				"  \"registration_number\": \"144/24\",\n" +
				"  \"student_id\": null\n" +
				"}";

		mockMvc.perform(post("/api/students")
				.with(httpBasic("stefania", "stefania"))
				.content(jsonStudent)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
		).andExpect(status().isCreated())
		.andExpect(jsonPath("email").value("pirvu.stefania@gmail.com"))
		.andExpect(jsonPath("student_id").isNumber());
	}
}

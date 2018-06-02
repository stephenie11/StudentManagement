package com.axway.univmgmt;

import com.axway.univmgmt.entity.Course;
import com.axway.univmgmt.entity.Student;
import com.axway.univmgmt.repository.CourseRepository;
import com.axway.univmgmt.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestHelper {

    public static final String TEST_CNP = "2970321035287";
    public static final String TEST_FIRST_NAME = "Alexandra";
    public static final String TEST_LAST_NAME = "Pirvu";
    public static final String TEST_EMAIL = "pirvu.alexandra@gmail.com";
    public static final String TEST_PHONE_NUMBER = "0739042107";
    public static final String TEST_REGISTRATION_NUMBER = "144/25";
    public static final int TEST_COLLEGE_YEAR = 1;
    public static final String TEST_COURSE_TITLE = "Calcul numeric";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Course course;

    private Student student;

    public Student createStudent() {
        if ( student == null  ) {
            student = new Student();
            student.setCNP(TEST_CNP);
            student.setFirstName(TEST_FIRST_NAME);
            student.setLastName(TEST_LAST_NAME);
            student.setEmail(TEST_EMAIL);
            student.setPhoneNumber(TEST_PHONE_NUMBER);
            student.setRegistrationNumber(TEST_REGISTRATION_NUMBER);
            student.setCollegeYear(TEST_COLLEGE_YEAR);
            student = studentRepository.save(student);
        }
        return student;
    }

    public Course createCourse() {
        if ( course == null ) {
            course = new Course();
            course.setTitle(TEST_COURSE_TITLE);
            course = courseRepository.save(course);
        }
        return course;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    @PostConstruct
    public void cleanDataBase() {
        course = null;
        student = null;
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }
}

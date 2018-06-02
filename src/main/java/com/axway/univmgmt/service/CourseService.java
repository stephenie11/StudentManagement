package com.axway.univmgmt.service;

import com.axway.univmgmt.entity.PageData;
import com.axway.univmgmt.entity.Course;
import com.axway.univmgmt.exception.StatusException;
import com.axway.univmgmt.exception.UnivMgmtException;
import com.axway.univmgmt.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse( String courseTitle ) throws UnivMgmtException {
        if ( courseTitle == null ) {
            throw new UnivMgmtException( StatusException.RESOURCE_BAD_REQUEST );
        }

        Course course;
        try {
            course = courseRepository.save(new Course(courseTitle));
        } catch ( Exception ex ) {
            throw new UnivMgmtException( StatusException.RESOURCE_CONFLICT );
        }

        return course;
    }

    public Course updateCourse( Long courseId, Course course ) throws UnivMgmtException {
        if ( course == null ) {
            throw new UnivMgmtException( StatusException.RESOURCE_BAD_REQUEST );
        }

        Optional<Course> existingCourse = courseRepository.findById( courseId);
        existingCourse.orElseThrow( () -> new UnivMgmtException(StatusException.RESOURCE_NO_CONTENT));
        existingCourse.get().setStudents( course.getStudents() );

        return courseRepository.save( existingCourse.get() );
    }

    public PageData< Course > listCourses ( Pageable pageable ) throws UnivMgmtException {
        if ( pageable == null ) {
            throw new UnivMgmtException( StatusException.RESOURCE_BAD_REQUEST );
        }

        PageData <Course> pageData = new PageData<>();
        Page pageCourses = courseRepository.findAll( pageable );
        pageData.setPageNumber( pageCourses.getNumber() );
        pageData.setPageSize( pageCourses.getSize() );
        pageData.setTotal( pageCourses.getTotalElements() );
        pageData.setData( pageCourses.getContent() );
        return pageData;
    }

    public List<Course> showAllCourses () {
        return courseRepository.findAll();
    }

    public void deleteCourse( Long courseId ) throws UnivMgmtException {
        if ( courseId == null ) {
            throw new UnivMgmtException( StatusException.RESOURCE_BAD_REQUEST );
        }

        Optional<Course> course = courseRepository.findById( courseId );
        course.orElseThrow( () -> new UnivMgmtException(StatusException.RESOURCE_NO_CONTENT));
        courseRepository.delete( course.get() );
    }
}

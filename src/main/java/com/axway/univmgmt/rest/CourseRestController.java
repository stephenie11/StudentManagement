package com.axway.univmgmt.rest;

import com.axway.univmgmt.entity.Course;
import com.axway.univmgmt.entity.PageData;
import com.axway.univmgmt.exception.UnivMgmtException;
import com.axway.univmgmt.service.CourseService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/courses")
@Api(
        value = "API to perform CRUD operations on a course",
        description = "This API provides tha capability to perform a CRUD operation on Course entity",
        produces = "appplication/json"
)
public class CourseRestController {

    private static final Logger logger = LoggerFactory.getLogger(CourseRestController.class);

    @Autowired
    private CourseService courseService;

    @ApiOperation( value = "Add a course" )
    @ApiResponses( value = {
            @ApiResponse( code = 201, message = "Created"),
            @ApiResponse( code = 400, message = "Bad Request"),
            @ApiResponse( code = 401, message = "Unauthorized"),
            @ApiResponse( code = 409, message = "Conflict"),
            @ApiResponse( code = 500, message = "Internal Server Error")
    } )
    @PostMapping
    public ResponseEntity <Course> addCourse (@RequestParam String courseTitle ) {
        logger.debug("Request to create a new course with course title {}... ", courseTitle);
        Course newCourse = null;

        try {
            newCourse = courseService.addCourse( courseTitle );
        } catch ( UnivMgmtException ex ) {
            logger.error("Failed to create a course due the following error: {}", ex.getMessage());
            return new ResponseEntity<>(newCourse, ex.getHttpStatus());
        }

        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @ApiOperation( value = "Update a course" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Success"),
            @ApiResponse( code = 204, message = "No content"),
            @ApiResponse( code = 400, message = "Bad Request"),
            @ApiResponse( code = 401, message = "Unauthorized"),
            @ApiResponse( code = 500, message = "Internal Server Error")
    } )
    @PatchMapping(value = "/{id}")
    public ResponseEntity <Course> updateCourse ( @PathVariable("id") Long courseId, @RequestBody Course course ) {
        logger.debug("Request to update an existing course ...");

        try {
            course = courseService.updateCourse( courseId, course );
        } catch ( UnivMgmtException ex ) {
            logger.error("Failed to update a course due the following error: {}", ex.getMessage());
            return new ResponseEntity<>(course, ex.getHttpStatus());
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @ApiOperation( value = "List courses" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Success"),
            @ApiResponse( code = 204, message = "No content"),
            @ApiResponse( code = 400, message = "Bad Request"),
            @ApiResponse( code = 401, message = "Unauthorized"),
            @ApiResponse( code = 500, message = "Internal Server Error")
    } )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
    })
    @GetMapping( value = "/list")
    public ResponseEntity <PageData<Course>> listCourses ( Pageable pageable ) {
        logger.debug("Request to list courses per pages ...");
        PageData <Course> coursesPageData = null;

        try {
            coursesPageData = courseService.listCourses( pageable );
        } catch ( UnivMgmtException ex ) {
            logger.error("Failed to list courses due the following error: {}", ex.getMessage());
            return new ResponseEntity<>(coursesPageData, ex.getHttpStatus());
        }

        return new ResponseEntity <>(coursesPageData, HttpStatus.OK);
    }

    @ApiOperation( value = "View courses")
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Success"),
            @ApiResponse( code = 401, message = "Unauthorized"),
            @ApiResponse( code = 500, message = "Internal Server Error")
    } )
    @GetMapping( value="/view" )
    public ResponseEntity < List<Course> > showAllCourses ( ) {
        logger.debug("Request to view courses ...");
        return new ResponseEntity<>(courseService.showAllCourses(), HttpStatus.OK);
    }

    @ApiOperation( value = "Delete a course")
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Success"),
            @ApiResponse( code = 400, message = "Bad Request"),
            @ApiResponse( code = 401, message = "Unauthorized"),
            @ApiResponse( code = 500, message = "Internal Server Error")
    } )
    @DeleteMapping
    public ResponseEntity deleteCourse( @RequestParam Long courseId ) {
        logger.debug("Request to delete course with id = {} ...", courseId);

        try {
            courseService.deleteCourse( courseId );
        } catch ( UnivMgmtException ex ) {
            logger.error("Failed to delete course due the following error: {}", ex.getMessage());
            return new ResponseEntity<>(ex.getHttpStatus());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

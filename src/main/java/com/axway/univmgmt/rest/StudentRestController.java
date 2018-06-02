package com.axway.univmgmt.rest;

import com.axway.univmgmt.entity.PageData;
import com.axway.univmgmt.entity.Student;
import com.axway.univmgmt.exception.UnivMgmtException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Api(
        value = "API to perform CRUD operations on a student",
        description = "This API provides tha capability to perform a CRUD operation on Student entity",
        produces = "appplication/json"
)
public class StudentRestController {

    private static final Logger logger = LoggerFactory.getLogger(StudentRestController.class);

    @Autowired
    private com.axway.univmgmt.service.StudentService studentService;

    @ApiOperation( value = "Add a Student" )
    @ApiResponses( value = {
            @ApiResponse( code = 201, message = "Created"),
            @ApiResponse( code = 400, message = "Bad Request"),
            @ApiResponse( code = 401, message = "Unauthorized"),
            @ApiResponse( code = 409, message = "Conflict"),
            @ApiResponse( code = 500, message = "Internal Server Error")
    } )
    @PostMapping
    public ResponseEntity<Student> addStudent (@RequestBody Student student ) {
        logger.debug("Request to create a new student ... ");

        try {
            student = studentService.addStudent( student );
        } catch ( UnivMgmtException ex ) {
            logger.error("Failed to create a student due the following error: {}", ex.getMessage());
            return new ResponseEntity<>(student, ex.getHttpStatus());
        }

        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @ApiOperation( value = "Replace a Student" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Success"),
            @ApiResponse( code = 204, message = "No content"),
            @ApiResponse( code = 400, message = "Bad Request"),
            @ApiResponse( code = 401, message = "Unauthorized"),
            @ApiResponse( code = 500, message = "Internal Server Error")
    } )
    @PutMapping("/{id}")
    public ResponseEntity <Student> replaceStudent ( @PathVariable("id") Long studentId, @RequestBody Student student ) {
        logger.debug("Request to update an existing student ...");

        try {
            student = studentService.replaceStudent( studentId, student );
        } catch ( UnivMgmtException ex ) {
            logger.error("Failed to update a student due the following error: {}", ex.getMessage());
            return new ResponseEntity<>(student, ex.getHttpStatus());
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @ApiOperation( value = "Update a Student" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Success"),
            @ApiResponse( code = 204, message = "No content"),
            @ApiResponse( code = 400, message = "Bad Request"),
            @ApiResponse( code = 401, message = "Unauthorized"),
            @ApiResponse( code = 500, message = "Internal Server Error")
    } )
    @PatchMapping("/{id}")
    public ResponseEntity <Student> updateStudent ( @PathVariable("id") Long studentId, @RequestBody Student student ) {
        logger.debug("Request to update an existing student ...");

        try {
            student = studentService.updateStudent( studentId, student );
        } catch ( UnivMgmtException ex ) {
            logger.error("Failed to update a student due the following error: {}", ex.getMessage());
            return new ResponseEntity<>(student, ex.getHttpStatus());
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @ApiOperation( value = "List Students" )
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
    public ResponseEntity <PageData<Student>> listStudents ( Pageable pageable ) {
        logger.debug("Request to list Students per pages ...");
        PageData <Student> studentsPageData = null;

        try {
            studentsPageData = studentService.listStudents( pageable );
        } catch ( UnivMgmtException ex ) {
            logger.error("Failed to list students due the following error: {}", ex.getMessage());
            return new ResponseEntity<>(studentsPageData, ex.getHttpStatus());
        }

        return new ResponseEntity <>(studentsPageData, HttpStatus.OK);
    }

    @ApiOperation( value = "View Students")
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Success"),
            @ApiResponse( code = 204, message = "No content"),
            @ApiResponse( code = 401, message = "Unauthorized"),
            @ApiResponse( code = 500, message = "Internal Server Error")
    } )
    @GetMapping( value="/view" )
    public ResponseEntity <List<Student>> showAllStudents ( ) {
        logger.debug("Request to view students ...");
        return new ResponseEntity<>(studentService.showAllStudents(), HttpStatus.OK);
    }

    @ApiOperation( value = "Delete a Student")
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Success"),
            @ApiResponse( code = 400, message = "Bad Request"),
            @ApiResponse( code = 401, message = "Unauthorized"),
            @ApiResponse( code = 500, message = "Internal Server Error")
    } )
    @DeleteMapping
    public ResponseEntity deleteStudent( @RequestParam Long studentId ) {
        logger.debug("Request to delete student with id = {} ...", studentId);

        try {
            studentService.deleteStudent( studentId );
        } catch ( UnivMgmtException ex ) {
            logger.error("Failed to delete student due the following error: {}", ex.getMessage());
            return new ResponseEntity<>(ex.getHttpStatus());
        }

        return new ResponseEntity <>(HttpStatus.OK);
    }
}

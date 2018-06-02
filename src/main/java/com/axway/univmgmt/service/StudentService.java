package com.axway.univmgmt.service;

import com.axway.univmgmt.entity.PageData;
import com.axway.univmgmt.entity.Student;
import com.axway.univmgmt.exception.StatusException;
import com.axway.univmgmt.exception.UnivMgmtException;
import com.axway.univmgmt.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent( Student student ) throws UnivMgmtException {
        if ( student == null ) {
            throw new UnivMgmtException( StatusException.RESOURCE_BAD_REQUEST );
        }

        Student newStudent;
        try {
            newStudent = studentRepository.save( student );
        } catch ( Exception ex ) {
            throw new UnivMgmtException( StatusException.RESOURCE_CONFLICT);
        }

        return newStudent;
    }

    public Student replaceStudent( Long studentId, Student student ) throws UnivMgmtException {
        if ( student == null || studentId == null ) {
            throw new UnivMgmtException( StatusException.RESOURCE_BAD_REQUEST );
        }

        Optional<Student> existingStudent = studentRepository.findById( studentId );
        existingStudent.orElseThrow( () -> new UnivMgmtException(StatusException.RESOURCE_NO_CONTENT));

        existingStudent.get().setFirstName( student.getFirstName());
        existingStudent.get().setLastName( student.getLastName());
        existingStudent.get().setEmail( student.getEmail() );
        existingStudent.get().setPhoneNumber( student.getPhoneNumber());
        existingStudent.get().setCollegeYear( student.getCollegeYear());
        existingStudent.get().setCNP( student.getCNP() );
        existingStudent.get().setRegistrationNumber( student.getRegistrationNumber() );
        existingStudent.get().setCourses( student.getCourses());

        return studentRepository.save( existingStudent.get());
    }

    public Student updateStudent( Long studentId, Student student ) throws UnivMgmtException {
        if ( student == null || studentId == null ) {
            throw new UnivMgmtException( StatusException.RESOURCE_BAD_REQUEST );
        }

        Optional<Student> existingStudent = studentRepository.findById( studentId );
        existingStudent.orElseThrow( () -> new UnivMgmtException(StatusException.RESOURCE_NO_CONTENT));

        existingStudent.get().setCollegeYear( student.getCollegeYear());
        existingStudent.get().setPhoneNumber( student.getPhoneNumber());
        existingStudent.get().setEmail( student.getEmail() );
        existingStudent.get().setFirstName( student.getFirstName());
        existingStudent.get().setLastName( student.getLastName());
        existingStudent.get().setCourses( student.getCourses());

        return studentRepository.save( existingStudent.get() );
    }

    public PageData< Student > listStudents ( Pageable pageable ) throws UnivMgmtException {
        if ( pageable == null ) {
            throw new UnivMgmtException( StatusException.RESOURCE_BAD_REQUEST );
        }

        PageData <Student> pageData = new PageData<>();
        Page pageStudents = studentRepository.findAll( pageable );
        pageData.setPageNumber( pageStudents.getNumber() );
        pageData.setPageSize( pageStudents.getSize() );
        pageData.setTotal( pageStudents.getTotalElements() );
        pageData.setData( pageStudents.getContent() );
        return pageData;
    }

    public List<Student> showAllStudents () {
        return studentRepository.findAll();
    }

    public void deleteStudent( Long studentId ) throws UnivMgmtException {
        if ( studentId == null ) {
            throw new UnivMgmtException( StatusException.RESOURCE_BAD_REQUEST );
        }
        Optional<Student> student = studentRepository.findById( studentId );
        student.orElseThrow( () -> new UnivMgmtException(StatusException.RESOURCE_NO_CONTENT));
        studentRepository.delete( student.get() );
    }
}

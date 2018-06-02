package com.axway.univmgmt.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    @JsonProperty( "student_id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "first_name")
    @JsonProperty( "first_name")
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Column(name = "last_name")
    @JsonProperty( "last_name")
    private String lastName;

    @NotNull
    @Column(name = "email", unique = true)
    @JsonProperty( "email" )
    private String email;

    @NotNull
    @Column(name= "phone_number", unique = true)
    @JsonProperty( "phone_number" )
    private String phoneNumber;

    @NotNull
    @Column(name = "cnp", unique = true)
    @JsonProperty( "cnp" )
    private String CNP;

    @NotNull
    @Column(unique = true, name = "registration_number")
    @JsonProperty( "registration_number" )
    private String registrationNumber;

    @NotNull
    @Column( name = "college_year")
    @JsonProperty( "college_year" )
    private int collegeYear;

    @ManyToMany( fetch = FetchType.LAZY , cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable( name = "student_course",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    @JsonIgnoreProperties("students")
    @JsonProperty("courses")
    private Set<Course> courses = new HashSet<>();

    public Student() {}

    public Student(Long id, @NotNull @Size(max = 20) String firstName, @NotNull @Size(max = 50) String lastName, @NotNull String email, @NotNull String phoneNumber, @NotNull String CNP, @NotNull String registrationNumber, @NotNull int collegeYear, Set<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.CNP = CNP;
        this.registrationNumber = registrationNumber;
        this.collegeYear = collegeYear;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getCollegeYear() {
        return collegeYear;
    }

    public void setCollegeYear(int collegeYear) {
        this.collegeYear = collegeYear;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}


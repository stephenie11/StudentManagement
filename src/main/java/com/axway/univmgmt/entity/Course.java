package com.axway.univmgmt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@ApiModel(description = "All details about a course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    @JsonProperty("course_id")
    private Long id;

    @NotNull
    @Column(name = "title", unique = true)
    @JsonProperty("course_title")
    private String title;

    @ManyToMany ( fetch = FetchType.LAZY, mappedBy = "courses", cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonIgnoreProperties("courses")
    @JsonProperty("students")
    private Set<Student> students = new HashSet<>();

    public Course() {}

    public Course(@NotNull String title) {
        this.title = title;
    }

    public Course(Long id, @NotNull String title, Set<Student> students) {
        this.id = id;
        this.title = title;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}

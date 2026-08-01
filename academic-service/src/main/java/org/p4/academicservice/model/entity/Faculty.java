package org.p4.academicservice.model.entity;

import jakarta.persistence.*;
import org.p4.academicservice.model.entity.base.Person;
import org.p4.academicservice.model.entity.enums.FacultyStatus;

import java.util.List;

@Entity
@Table(name = "faculty")
public class Faculty extends Person {

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FacultyStatus status;

    @OneToMany(mappedBy = "faculty")
    private List<Course> courseList;

    public Faculty() {}

    public Faculty(String firstName, String lastName, String email){
        super(firstName, lastName, email);
    }

    public FacultyStatus getStatus() {
        return status;
    }

    public void setStatus(FacultyStatus status) {
        this.status = status;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public void addCourse(Course course){
        if(course == null || courseList.contains(course)){
            return;
        }

        this.courseList.add(course);
    }

    public void removeCourse(Course course){
        if(course == null || !courseList.contains(course)){
            return;
        }

        course.setFaculty(null);
        this.courseList.remove(course);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                "status=" + status +
                ", courseList=" + courseList +
                '}';
    }
}

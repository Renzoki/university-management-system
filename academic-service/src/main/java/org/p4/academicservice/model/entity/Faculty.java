package org.p4.academicservice.model.entity;

import jakarta.persistence.*;
import org.p4.academicservice.model.entity.base.Person;
import org.p4.academicservice.model.entity.enums.FacultyStatus;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "faculty")
public class Faculty extends Person {

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FacultyStatus status;

    @OneToMany(mappedBy = "faculty")
    private List<Course> courseList;

    public Faculty() {}

    public Faculty(UUID id, String firstName, String lastName, String email){
        super(id, firstName, lastName, email);
        this.status = FacultyStatus.ACTIVE;
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

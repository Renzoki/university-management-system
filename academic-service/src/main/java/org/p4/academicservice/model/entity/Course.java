package org.p4.academicservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.p4.academicservice.model.entity.base.BaseEntity;
import org.p4.academicservice.model.entity.enums.CourseStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course extends BaseEntity {

    @Column(name = "course_name", nullable = false, unique = true)
    private String courseName;

    @Size(max = 7)
    @Column(name = "course_code", nullable = false, unique = true, length = 7)
    private String courseCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_status", nullable = false)
    private CourseStatus status;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Enrollment> enrollmentList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    public Course() {}

    public Course(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.status = CourseStatus.OFFERED;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Enrollment> getEnrollmentList() {
        return enrollmentList;
    }

    public void setEnrollmentList(List<Enrollment> enrollmentList) {
        this.enrollmentList = enrollmentList;
    }

    public void addEnrollment(Enrollment enrollment){
        if(enrollment == null)
            return;

        enrollmentList.add(enrollment);
        enrollment.setCourse(this);
    }

    public void removeEnrollment(Enrollment enrollment){
        if(enrollment == null)
            return;

        enrollmentList.remove(enrollment);
        enrollment.setCourse(null);
    }

    public void assignFaculty(Faculty faculty){
        if (faculty == null || this.faculty == faculty) {
            return;
        }

        if (this.faculty != null) {
            this.faculty.removeCourse(this);
        }

        this.setFaculty(faculty);
        faculty.addCourse(this);
    }

    @Override
    public String toString() {
        return "Course{" +
                " courseName='" + courseName + '\'' +
                ", oldCourseCode='" + courseCode + '\'' +
                ", courseStatus='" + status + '\'' +
                '}';
    }
}

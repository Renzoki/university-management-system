package org.p4.academicservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.p4.academicservice.model.entity.base.BaseEntity;
import org.p4.academicservice.model.entity.enums.CourseStatus;

import java.util.List;
import java.util.UUID;

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
    private List<Enrollment> enrollmentList;

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

    @Override
    public String toString() {
        return "Course{" +
                " courseName='" + courseName + '\'' +
                ", oldCourseCode='" + courseCode + '\'' +
                ", courseStatus='" + status + '\'' +
                '}';
    }
}

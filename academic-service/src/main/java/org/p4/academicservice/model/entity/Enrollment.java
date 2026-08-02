package org.p4.academicservice.model.entity;

import jakarta.persistence.*;
import org.p4.academicservice.model.entity.base.BaseEntity;
import org.p4.academicservice.model.entity.enums.EnrollmentStatus;

@Entity
@Table(name = "enrollment")
public class Enrollment extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    EnrollmentStatus status;

    public Enrollment() {}

    public Enrollment(Student student, Course course){
        this.student = student;
        this.course = course;
        this.status = EnrollmentStatus.ACTIVE;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }
}

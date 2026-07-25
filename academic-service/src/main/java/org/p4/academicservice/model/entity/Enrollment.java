package org.p4.academicservice.model.entity;

import jakarta.persistence.*;
import org.p4.academicservice.model.entity.base.BaseEntity;

@Entity
@Table(name = "enrollment")
public class Enrollment extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Grade grade;

    public Enrollment() {}

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
}

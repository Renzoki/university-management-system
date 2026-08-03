package org.p4.academicservice.model.entity;


import jakarta.persistence.*;
import org.p4.academicservice.model.entity.base.Person;
import org.p4.academicservice.model.entity.enums.StudentStatus;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student")
public class Student extends Person {
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StudentStatus status;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Enrollment> enrollmentList;

    public Student() {}

    public Student(UUID id, String firstName, String lastName, String email) {
        this.status = StudentStatus.NOT_ENROLLED;
        super(id, firstName, lastName, email);
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
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
        enrollment.setStudent(this);
    }

    public void removeEnrollment(Enrollment enrollment){
        if(enrollment == null)
            return;

        enrollmentList.remove(enrollment);
        enrollment.setStudent(null);
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", status=" + status +
                '}';
    }
}

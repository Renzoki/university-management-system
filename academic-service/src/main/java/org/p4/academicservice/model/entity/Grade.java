package org.p4.academicservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.p4.academicservice.model.entity.base.BaseEntity;

@Entity
public class Grade extends BaseEntity {

    @Column(name = "raw_grade")
    private double rawGrade;

    @Column(name = "grade_equivalent")
    private double gradeEquivalent;

    @OneToOne(optional = false)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    public Grade() {}

    public Grade(double rawGrade, Enrollment enrollment){
        this.rawGrade = rawGrade;
        this.enrollment = enrollment;
        setEnrollment(enrollment);
    }

    public double getGradeEquivalent() {
        return gradeEquivalent;
    }

    public void setGradeEquivalent(double gradeEquivalent) {
        this.gradeEquivalent = gradeEquivalent;
    }

    public double getRawGrade() {
        return rawGrade;
    }

    public void setRawGrade(double rawGrade) {
        this.rawGrade = rawGrade;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
        if (enrollment != null && enrollment.getGrade() != this) {
            enrollment.setGrade(this);
        }
    }

    @Override
    public String toString() {
        return "Grade{" +
                "rawGrade=" + rawGrade +
                ", gradeEquivalent=" + gradeEquivalent +
                '}';
    }
}

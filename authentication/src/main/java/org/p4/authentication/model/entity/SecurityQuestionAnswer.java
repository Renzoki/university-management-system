package org.p4.authentication.model.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class SecurityQuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "question")
    private SecurityQuestion question;

    @Column(name = "answerHash")
    private String answerHash;

    public SecurityQuestionAnswer() { }

    public SecurityQuestionAnswer(SecurityQuestion question, String answerHash) {
        this.question = question;
        this.answerHash = answerHash;
    }

    public UUID getId() {
        return id;
    }

    public SecurityQuestion getQuestion() {
        return question;
    }

    public void setQuestion(SecurityQuestion question) {
        this.question = question;
    }

    public String getAnswerHash() {
        return answerHash;
    }

    public void setAnswerHash(String answerHash) {
        this.answerHash = answerHash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SecurityQuestionAnswer that = (SecurityQuestionAnswer) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

package org.p4.authentication.model.entity;

public enum SecurityQuestion {
    FIRST_PET("What was the name of your first pet?"),
    BIRTH_CITY("What city were you born in?"),
    CHILDHOOD_NICKNAME("What was your childhood nickname?"),
    FIRST_SCHOOL("What was the name of your first school?"),
    FICTIONAL_CHARACTER("What is the name of your favorite fictional character?");

    private final String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public String getQuestion(){
        return question;
    }
}

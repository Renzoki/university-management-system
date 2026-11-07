package org.p4.authentication.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.p4.authentication.model.entity.SecurityQuestion;

public record QuestionAnswerPair(
        @NotNull(message = NULL_QUESTION)
        SecurityQuestion question,

        @NotBlank(message = BLANK_ANSWER)
        @Size(min = 1, max = 30, message = ANSWER_LENGTH)
        String answer
) {
    private final static String NULL_QUESTION = "Question cannot be null!";
    private final static String BLANK_ANSWER = "Answer cannot be blank!";
    private final static String ANSWER_LENGTH = "Answer cannot exceed 30 characters!";
}

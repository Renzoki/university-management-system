package org.p4.authentication.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record VerificationQuestionsRequest(
        @Size(min = 3, max = 5, message = INVALID_QUESTION_ANSWER_AMOUNT)
        @NotNull(message = NULL_QUESTIONS_ANSWERS)
        List<QuestionAnswerPair> QuestionsAndAnswers
) {
    private final static String INVALID_QUESTION_ANSWER_AMOUNT = "Questions answered must be between 3 and 5!";
    private final static String NULL_QUESTIONS_ANSWERS = "Questions and nswers must be null!";

}

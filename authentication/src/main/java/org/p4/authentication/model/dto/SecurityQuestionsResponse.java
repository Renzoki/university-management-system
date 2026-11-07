package org.p4.authentication.model.dto;

import org.p4.authentication.model.entity.SecurityQuestion;

public record SecurityQuestionsResponse(
        SecurityQuestion[] securityQuestions
) {
}

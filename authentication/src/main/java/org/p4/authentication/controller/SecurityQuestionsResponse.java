package org.p4.authentication.controller;

import org.p4.authentication.model.entity.SecurityQuestion;

public record SecurityQuestionsResponse(
        SecurityQuestion[] securityQuestions
) {
}

package org.p4.authentication.client.response;

import java.util.UUID;

public record EnrollmentResponse(
        UUID enrollmentId
) {
}
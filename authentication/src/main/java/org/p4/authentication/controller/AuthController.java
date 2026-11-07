package org.p4.authentication.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.p4.authentication.configuration.AuthenticatedUser;
import org.p4.authentication.model.dto.SecurityQuestionsResponse;
import org.p4.authentication.model.dto.VerificationQuestionsRequest;
import org.p4.authentication.model.entity.Action;
import org.p4.authentication.model.entity.AuthLog;
import org.p4.authentication.model.entity.SecurityQuestion;
import org.p4.authentication.service.AuthLogService;
import org.p4.authentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.p4.authentication.model.dto.LoginRequest;
import org.p4.authentication.model.dto.LoginResponse;
import org.p4.authentication.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthLogService logService;
    private final UserService userService;

    public AuthController(AuthService authService, AuthLogService logService, UserService userService){
        this.authService = authService;
        this.logService = logService;
        this.userService = userService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> handleLogin(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request){
        AuthLog log = logService.logEvent(loginRequest.email(), request.getRemoteAddr(), Action.LOGIN);
        String accessToken = authService.authenticateUser(loginRequest);
        logService.markAsSuccessful(log);
        return ResponseEntity.ok(new LoginResponse(accessToken));
    }

    @GetMapping("/security-questions")
    public ResponseEntity<SecurityQuestionsResponse> getSecurityQuestions(
            @AuthenticationPrincipal AuthenticatedUser user,
            HttpServletRequest request
    ){
        AuthLog log = logService.logEvent(user.email(), request.getRemoteAddr(), user.role(), Action.GET_QUESTIONS);
        SecurityQuestionsResponse response = new SecurityQuestionsResponse(SecurityQuestion.values());
        logService.markAsSuccessful(log);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/security-answers")
    public ResponseEntity<Void> addSecurityQuestionAnswers(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody VerificationQuestionsRequest verificationQuestionsRequest,
            HttpServletRequest request
    ){
        AuthLog log = logService.logEvent(user.email(), request.getRemoteAddr(), user.role(), Action.SUBMIT_SECURITY_QUESTION);
        userService.saveAuthenticationQuestionAnswers(user.id(), verificationQuestionsRequest);
        logService.markAsSuccessful(log);
        return ResponseEntity.noContent().build();
    }
}

package org.p4.authentication.service;

import org.p4.authentication.model.dto.QuestionAnswerPair;
import org.p4.authentication.model.dto.VerificationQuestionsRequest;
import org.p4.authentication.model.entity.SecurityQuestionAnswer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.p4.authentication.exception.ResourceNotFoundException;
import org.p4.authentication.model.entity.User;
import org.p4.authentication.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void saveAuthenticationQuestionAnswers(String userEmail, VerificationQuestionsRequest request){
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

        for(QuestionAnswerPair s : request.QuestionsAndAnswers()){
            String cleanedAnswer = s.answer().trim().toLowerCase();
            SecurityQuestionAnswer qa = new SecurityQuestionAnswer(s.question(), encoder.encode(cleanedAnswer));
            user.addVerificationQuestion(qa);
        }

        userRepository.save(user);
    }
}

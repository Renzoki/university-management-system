package org.p4.authentication.service;

import org.p4.authentication.model.entity.Action;
import org.p4.authentication.model.entity.ActionStatus;
import org.p4.authentication.model.entity.AuthLog;
import org.p4.authentication.repository.AuthLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthLogService {
    private AuthLogRepository repository;

    public AuthLogService(AuthLogRepository repository){
        this.repository = repository;
    }

    public AuthLog logEvent(String userIdentifier, String userIp, Action action){
        return repository.save(new AuthLog(userIdentifier, userIp, action));
    }

    public void markAsSuccessful(AuthLog log){
        log.setStatus(ActionStatus.SUCCESS);
        repository.save(log);
    }
}

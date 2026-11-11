package org.p4.authentication.service;

import org.p4.authentication.model.entity.Action;
import org.p4.authentication.model.entity.ActionStatus;
import org.p4.authentication.model.entity.EventLog;
import org.p4.authentication.model.entity.UserRole;
import org.p4.authentication.repository.EventLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventLogService {
    private EventLogRepository repository;

    public EventLogService(EventLogRepository repository){
        this.repository = repository;
    }


    public EventLog logEvent(String userIdentifier, String userIp, Action action){
        LocalDateTime timeStamp = LocalDateTime.now();
        return repository.save(new EventLog(userIdentifier, userIp, action, timeStamp));
    }

    public EventLog logEvent(String userIdentifier, String userIp, UserRole userRole, Action action){
        LocalDateTime timeStamp = LocalDateTime.now();
        return repository.save(new EventLog(userIdentifier, userIp, userRole, action, timeStamp));
    }

    public void markAsSuccessful(EventLog log){
        log.setStatus(ActionStatus.SUCCESS);
        repository.save(log);
    }
}

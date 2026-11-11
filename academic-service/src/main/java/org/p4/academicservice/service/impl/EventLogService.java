package org.p4.academicservice.service.impl;

import org.p4.academicservice.model.entity.EventLog;
import org.p4.academicservice.repository.EventLogRepository;
import org.p4.academicservice.model.entity.enums.Action;
import org.p4.academicservice.model.entity.enums.ActionStatus;
import org.p4.academicservice.model.entity.enums.UserRole;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventLogService {
    private final EventLogRepository repository;

    public EventLogService(EventLogRepository repository){
        this.repository = repository;
    }

    public EventLog logEvent(UUID actorId, String actorIp, UserRole actorRole, Action action,
                             UUID personId, UserRole personRole){
        LocalDateTime timeStamp = LocalDateTime.now();
        EventLog log = new EventLog(actorId, actorIp, actorRole, action, personId, personRole, timeStamp);
        return repository.save(log);
    }

    public void markAsSuccessful(EventLog log){
        log.setStatus(ActionStatus.SUCCESS);
        repository.save(log);
    }
}

package org.p4.academicservice.model.entity;

import jakarta.persistence.*;
import org.p4.academicservice.model.entity.base.BaseEntity;
import org.p4.academicservice.model.entity.enums.Action;
import org.p4.academicservice.model.entity.enums.ActionStatus;
import org.p4.academicservice.model.entity.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event_logs")
public class EventLog extends BaseEntity {

    @Column(name = "actor_identifier", nullable = false, updatable = false)
    private String actorIdentifier;

    @Column(name = "actor_ip", nullable = false, updatable = false)
    private String actorIp;

    @Column(name = "actor_role", updatable = false)
    private UserRole actorRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, updatable = false)
    private Action action;

    @Column(name = "person_rd", nullable = false, updatable = false)
    private UUID personId;

    @Column(name = "person_role", nullable = false, updatable = false)
    private UUID personRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_status", nullable = false, updatable = false)
    private ActionStatus status;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    private void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }
    public EventLog() {}

    public EventLog(String actorIdentifier, String actorIp, UserRole actorRole, Action action,
                    UUID personId, UUID personRole, LocalDateTime timestamp) {
        this.actorIdentifier = actorIdentifier;
        this.actorIp = actorIp;
        this.actorRole = actorRole;
        this.action = action;
        this.personId = personId;
        this.personRole = personRole;
        this.timestamp = timestamp;
        this.status = ActionStatus.FAILURE;
    }

    public String getActorIdentifier() {
        return actorIdentifier;
    }

    public String getActorIp() {
        return actorIp;
    }

    public UserRole getActorRole() {
        return actorRole;
    }

    public void setActorRole(UserRole userRole) {
        this.actorRole = userRole;
    }

    public Action getAction() {
        return action;
    }

    public ActionStatus getStatus() {
        return status;
    }

    public void setStatus(ActionStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

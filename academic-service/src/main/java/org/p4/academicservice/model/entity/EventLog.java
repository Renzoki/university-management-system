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
    private UUID actorId;

    @Column(name = "actor_ip", nullable = false, updatable = false)
    private String actorIp;

    @Column(name = "actor_role", updatable = false)
    private UserRole actorRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, updatable = false)
    private Action action;

    @Column(name = "person_id", updatable = false)
    private UUID personId;

    @Enumerated(EnumType.STRING)
    @Column(name = "person_role", updatable = false)
    private UserRole personRole;

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

    public EventLog(UUID actorId, String actorIp, UserRole actorRole, Action action,
                    UUID personId, UserRole personRole, LocalDateTime timestamp) {
        this.actorId = actorId;
        this.actorIp = actorIp;
        this.actorRole = actorRole;
        this.action = action;
        this.personId = personId;
        this.personRole = personRole;
        this.timestamp = timestamp;
        this.status = ActionStatus.FAILURE;
    }

    public UUID getActorId() {
        return actorId;
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

    public UUID getPersonId() {
        return personId;
    }

    public UserRole getPersonRole() {
        return personRole;
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

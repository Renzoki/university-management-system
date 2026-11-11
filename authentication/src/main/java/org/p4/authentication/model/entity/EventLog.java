package org.p4.authentication.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "auth_logs")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "user_identifier", nullable = false, updatable = false)
    private String userIdentifier;

    @Column(name = "user_ip", nullable = false, updatable = false)
    private String userIp;

    @Column(name = "user_role", updatable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, updatable = false)
    private Action action;

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

    public EventLog(String userIdentifier, String userIp, UserRole role, Action action, LocalDateTime timeStamp) {
        this.userRole = role;
        this(userIdentifier, userIp, action, timeStamp);
    }

    public EventLog(String userIdentifier, String userIp, Action action, LocalDateTime timeStamp) {
        this.userIdentifier = userIdentifier;
        this.userIp = userIp;
        this.action = action;
        this.timestamp = timeStamp;
        this.userRole = UserRole.UNAUTHENTICATED;
        this.status = ActionStatus.FAILURE;
    }

    public UUID getId() {
        return id;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public String getUserIp() {
        return userIp;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventLog eventLog = (EventLog) o;
        return Objects.equals(id, eventLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

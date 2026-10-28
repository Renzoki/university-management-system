package org.p4.authentication.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "auth_logs")
public class AuthLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "user_identifier", nullable = false, updatable = false)
    private String userIdentifier;

    @Column(name = "user_ip", nullable = false, updatable = false)
    private String userIp;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, updatable = false)
    private Action action;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_status", nullable = false, updatable = false)
    private ActionStatus actionStatus;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    private void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }
    public AuthLog() {}

    public AuthLog(String userIdentifier, String userIp, Action action, ActionStatus actionStatus) {
        this.userIdentifier = userIdentifier;
        this.userIp = userIp;
        this.action = action;
        this.actionStatus = actionStatus;
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

    public Action getAction() {
        return action;
    }

    public ActionStatus getActionStatus() {
        return actionStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthLog authLog = (AuthLog) o;
        return Objects.equals(id, authLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

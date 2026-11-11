package org.p4.academicservice.repository;

import org.p4.academicservice.model.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventLogRepository extends JpaRepository<EventLog, UUID> {
}

package org.p4.academicservice.model.entity.base;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    protected BaseEntity(){}

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity baseEntity = (BaseEntity) o;
        return Objects.equals(id, baseEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

package com.parceltracker.parceltracking.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class VersionedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Version
    private int version;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VersionedEntity that = (VersionedEntity) o;
        return version == that.version && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    public UUID getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }
}

package com.placecruncher.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Entity<Integer>
{
    private Date created;
    private Date updated;
    private int version = -1;

    @Override
    @Column(name = "CREATED", insertable = true, updatable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    @Column(name = "UPDATED", insertable = true, updatable = true)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    @Version
    @Column(name = "VERSION", nullable = false)
    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        Integer thatId = ((AbstractEntity) obj).getId();
        return this.getId() == null ? thatId == null : this.getId().equals(thatId);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }

    /** {@inheritDoc} */
    @PrePersist
    public void onPrePersist() {
        created = new Date();
    }

    /** {@inheritDoc} */
    @PreUpdate
    public void onPreUpdate() {
        updated = new Date();
    }
}

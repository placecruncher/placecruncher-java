package com.placecruncher.server.controller;


public abstract class EntityModel {

    public abstract Integer getId();

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;

        Integer thatId = ((EntityModel) obj).getId();
        return this.getId() == null ? thatId == null : this.getId().equals(thatId);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }
}

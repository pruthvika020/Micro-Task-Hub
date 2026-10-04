package com.example.model;

import java.io.Serializable;
import java.util.Objects;

public class RolesMappingId implements Serializable {
    int mid;
    int role;

    public RolesMappingId() {}

    public RolesMappingId(int mid, int role) {
        this.mid = mid;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolesMappingId)) return false;
        RolesMappingId that = (RolesMappingId) o;
        return mid == that.mid && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid, role);
    }
}

package com.remcoil.timetracker.users.core.data;

public enum UserRole {
    Employee,
    Admin;

    public String toSecurityRole() {
        return "ROLE_" + this.name();
    }
}

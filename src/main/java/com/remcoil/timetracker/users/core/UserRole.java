package com.remcoil.timetracker.users.core;

public enum UserRole {
    Employee,
    Admin;

    public String toSecurityRole() {
        return "ROLE_" + this.name();
    }
}

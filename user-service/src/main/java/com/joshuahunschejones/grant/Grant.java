package com.joshuahunschejones.grant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grant {
    @JsonProperty
    private long id;

    @JsonProperty
    private long userId;

    @JsonProperty
    private long roleId;

    @JsonProperty
    private long accountId;

    // empty constructor required by Jackson deserialization
    public Grant() {
    }

    public Grant(long id, long userId, long roleId, long accountId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.accountId = accountId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public long getAccountId() {
        return accountId;
    }
}

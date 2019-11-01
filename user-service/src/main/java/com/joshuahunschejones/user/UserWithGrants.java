package com.joshuahunschejones.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joshuahunschejones.grant.Grant;

import java.util.List;

public class UserWithGrants {
    @JsonProperty
    private long id;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private String email;

    @JsonProperty
    private List<Grant> grants;

    // empty constructor required by Jackson deserialization
    public UserWithGrants() {
    }

    public UserWithGrants(long id, String firstName, String lastName, String email, List<Grant> grants) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.grants = grants;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Grant> getGrants() {
        return grants;
    }
}


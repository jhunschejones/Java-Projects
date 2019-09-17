package com.joshuahunschejones;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NamedQueries({
        // When you write HQL (or JPQL) queries, you use the names of the types, not the tables.
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findByName",
                query = "SELECT u FROM User u WHERE u.firstName LIKE :name OR u.lastName LIKE :name"),
        @NamedQuery(name = "User.remove", query = "DELETE FROM User u WHERE u.id = :id"),
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    // empty constructor required by Jackson deserialization
    public User() {

    }

    public User(long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

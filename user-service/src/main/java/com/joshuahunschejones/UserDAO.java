package com.joshuahunschejones;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> findAll() {
        return (List<User>) list(namedQuery("User.findAll"));
    }

    public List<User> findByName(String name) {
        StringBuilder builder = new StringBuilder("%");
        builder.append(name).append("%");
        return (List<User>) list(namedQuery("User.findByName").setParameter("name", builder.toString()));
    }

    public Optional<User> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public User save(User user) {
        return persist(user);
    }

    public void delete(User user) {
        namedQuery("User.remove").setParameter("id", user.getId()).executeUpdate();
    }
}

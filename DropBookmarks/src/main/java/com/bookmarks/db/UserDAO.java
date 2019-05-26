package com.bookmarks.db;

import com.bookmarks.core.User;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAO extends AbstractDAO<User> {
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> findAll() {
        return list(namedQuery("com.bookmarks.core.User.findAll"));
    }

    public Optional<User> findByUserNamePassword(String userName, String password) {
        return Optional.fromNullable(
                uniqueResult(
                        namedQuery("com.bookmarks.core.User.findByUserNamePassword")
                        .setParameter("username", userName)
                        .setParameter("password", password)
                )
        );
    }
}

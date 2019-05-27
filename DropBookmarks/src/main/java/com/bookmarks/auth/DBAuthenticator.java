package com.bookmarks.auth;

import com.bookmarks.core.User;
import com.bookmarks.db.UserDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class DBAuthenticator implements Authenticator<BasicCredentials, User> {

    private UserDAO userDAO;

    public DBAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        return userDAO.findByUserNamePassword(credentials.getUsername(), credentials.getPassword());
    }
}

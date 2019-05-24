package com.bookmarks.access;

import com.bookmarks.core.User;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class HelloAccess implements Authenticator<BasicCredentials, User> {
    private String password;

    public HelloAccess(String password) {
        this.password = password;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        if(password.equals(basicCredentials.getPassword())) {
            return Optional.of(new User());
        } else {
            return Optional.absent();
        }
    }
}

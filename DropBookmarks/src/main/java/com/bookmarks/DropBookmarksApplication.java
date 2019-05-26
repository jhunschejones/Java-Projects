package com.bookmarks;

import com.bookmarks.access.HelloAccess;
import com.bookmarks.core.User;
import com.bookmarks.resources.HelloResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropBookmarksApplication extends Application<DropBookmarksConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DropBookmarksApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropBookmarks";
    }

    @Override
    public void initialize(final Bootstrap<DropBookmarksConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<DropBookmarksConfiguration>() {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(DropBookmarksConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(final DropBookmarksConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(
                new HelloResource()
        );
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new HelloAccess(configuration.getPassword()))
                        .setRealm("SECURITY REALM")
                        .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

}

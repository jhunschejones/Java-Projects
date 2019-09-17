package com.joshuahunschejones;

import com.joshuahunschejones.config.Configuration;
import com.joshuahunschejones.resource.UserResource;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Application extends io.dropwizard.Application<Configuration> {

    private final HibernateBundle<Configuration> hibernateBundle = new HibernateBundle<Configuration>(User.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(Configuration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new Application().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final Configuration configuration, final Environment environment) {
        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new UserResource(userDAO));
    }
}
package com.joshuahunschejones;

import com.joshuahunschejones.config.Configuration;
import com.joshuahunschejones.grant.GrantDAO;
import com.joshuahunschejones.resource.GrantResource;
import com.joshuahunschejones.resource.UserResource;
import com.joshuahunschejones.user.UserDAO;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

public class Application extends io.dropwizard.Application<Configuration> {

    public static void main(final String[] args) throws Exception {
        new Application().run(args);
    }

    @Override
    public void run(final Configuration configuration, final Environment environment) {
        final Jdbi jdbi = new JdbiFactory().build(environment, configuration.getDataSourceFactory(), "MySQL");
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
        environment.jersey().register(new UserResource(userDAO));
        final GrantDAO grantDAO = jdbi.onDemand(GrantDAO.class);
        environment.jersey().register(new GrantResource(grantDAO));

        environment.healthChecks().register("API", new HealthCheck());
    }
}

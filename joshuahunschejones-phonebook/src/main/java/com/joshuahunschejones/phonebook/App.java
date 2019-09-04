package com.joshuahunschejones.phonebook;

import com.joshuahunschejones.resources.ClientResource;
import com.joshuahunschejones.resources.ContactResource;
import com.sun.jersey.api.client.Client;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<PhonebookConfiguration> {
    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> bootstrap) {

    }

    @Override
    public void run(PhonebookConfiguration c, Environment e) throws Exception {
        LOGGER.info("Method App#run() called");
        System.out.println(c.getMessage());
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(e, c.getDataSourceFactory(), "mysql");
        e.jersey().register(new ContactResource(jdbi, e.getValidator()));

        final Client client = new JerseyClientBuilder(e).build("REST Client");
        e.jersey().register(new ClientResource(client));
    }
}

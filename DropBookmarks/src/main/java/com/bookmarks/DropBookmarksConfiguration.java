package com.bookmarks;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

public class DropBookmarksConfiguration extends Configuration {

    @NotEmpty // prevent application from starting if no value found in config.yml
    private String password;

    @NotNull
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty("database") // point to section in config.yml
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }
}

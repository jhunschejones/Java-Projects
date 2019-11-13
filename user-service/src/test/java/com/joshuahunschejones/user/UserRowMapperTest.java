package com.joshuahunschejones.user;

import org.jdbi.v3.core.statement.StatementContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRowMapperTest {
    @Mock
    private
    ResultSet resultSet;

    @Mock
    private
    StatementContext statementContext;

    @Test
    public void readUser() throws SQLException {
        final UserRowMapper userRowMapper = new UserRowMapper();
        when(resultSet.getLong(eq("id"))).thenReturn(1L);
        when(resultSet.getString(eq("first_name"))).thenReturn("Carl");
        when(resultSet.getString(eq("last_name"))).thenReturn("Fox");
        when(resultSet.getString(eq("email"))).thenReturn("carl@dafox.com");

        final User user = userRowMapper.map(resultSet, statementContext);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("Carl");
        assertThat(user.getLastName()).isEqualTo("Fox");
        assertThat(user.getEmail()).isEqualTo("carl@dafox.com");
    }
}

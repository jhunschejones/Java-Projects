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
public class UserWithGrantsMapperTest {
    @Mock
    private
    ResultSet resultSet;

    @Mock
    private
    StatementContext statementContext;

    @Test
    public void readUserWithGrants() throws SQLException {
        final UserWithGrantsMapper userWithGrantsMapper = new UserWithGrantsMapper();
        when(resultSet.getLong(eq("id"))).thenReturn(1L);
        when(resultSet.getString(eq("first_name"))).thenReturn("Carl");
        when(resultSet.getString(eq("last_name"))).thenReturn("Fox");
        when(resultSet.getString(eq("email"))).thenReturn("carl@dafox.com");
        when(resultSet.getLong(eq("grant_id"))).thenReturn(2L);
        when(resultSet.getLong(eq("role_id"))).thenReturn(3L);
        when(resultSet.getLong(eq("account_id"))).thenReturn(5L);

        final UserWithGrants userWithGrants = userWithGrantsMapper.map(resultSet, statementContext);

        assertThat(userWithGrants.getId()).isEqualTo(1L);
        assertThat(userWithGrants.getFirstName()).isEqualTo("Carl");
        assertThat(userWithGrants.getLastName()).isEqualTo("Fox");
        assertThat(userWithGrants.getEmail()).isEqualTo("carl@dafox.com");
        assertThat(userWithGrants.getGrants().get(0).getId()).isEqualTo(2L);
        assertThat(userWithGrants.getGrants().get(0).getUserId()).isEqualTo(1L);
        assertThat(userWithGrants.getGrants().get(0).getRoleId()).isEqualTo(3L);
        assertThat(userWithGrants.getGrants().get(0).getAccountId()).isEqualTo(5L);
    }
}

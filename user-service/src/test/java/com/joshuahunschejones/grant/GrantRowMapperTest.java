package com.joshuahunschejones.grant;

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
public class GrantRowMapperTest {
    @Mock
    private
    ResultSet resultSet;

    @Mock
    private
    StatementContext statementContext;

    @Test
    public void readGrant() throws SQLException {
        final GrantRowMapper grantRowMapper = new GrantRowMapper();
        when(resultSet.getLong(eq("id"))).thenReturn(1L);
        when(resultSet.getLong(eq("role_id"))).thenReturn(3L);
        when(resultSet.getLong(eq("account_id"))).thenReturn(5L);
        when(resultSet.getLong(eq("user_id"))).thenReturn(6L);

        final Grant grant = grantRowMapper.map(resultSet, statementContext);

        assertThat(grant.getId()).isEqualTo(1L);
        assertThat(grant.getRoleId()).isEqualTo(3L);
        assertThat(grant.getAccountId()).isEqualTo(5L);
        assertThat(grant.getUserId()).isEqualTo(6L);
    }
}

package com.joshuahunschejones.grant;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GrantRowMapper implements RowMapper<Grant> {

    @Override
    public Grant map(ResultSet resultSet, StatementContext ctx) throws SQLException {
        return new Grant(
                resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getLong("role_id"),
                resultSet.getLong("account_id")
        );
    }
}

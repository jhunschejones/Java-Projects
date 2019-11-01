package com.joshuahunschejones.user;

import com.joshuahunschejones.grant.Grant;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserWithGrantsMapper implements RowMapper<UserWithGrants> {
    private UserWithGrants userWithGrants;

    @Override
    public UserWithGrants map(ResultSet rs, StatementContext ctx) throws SQLException {

        if (userWithGrants == null) {
            userWithGrants = new UserWithGrants(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), new ArrayList<Grant>());
        }

        Grant grant = new Grant(rs.getLong("grant_id"), userWithGrants.getId(), rs.getLong("role_id"), rs.getLong("account_id"));
        userWithGrants.getGrants().add(grant);
        return userWithGrants;
    }
}

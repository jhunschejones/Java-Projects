package com.joshuahunschejones.grant;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(GrantRowMapper.class)
public interface GrantDAO {

    @SqlQuery("SELECT * FROM grants WHERE user_id = :userId AND role_id = :roleId AND account_id = :accountId")
    Optional<Grant> find(@BindBean Grant grant);

    @SqlQuery("SELECT * FROM grants WHERE user_id = :id")
    List<Grant> findByUserId(@Bind("id") long id);

    @SqlQuery("SELECT * FROM grants WHERE account_id = :id")
    List<Grant> findByAccountId(@Bind("id") long id);

    @SqlUpdate("INSERT INTO grants(user_id, role_id, account_id) VALUES(:userId, :roleId, :accountId)")
    int create(@BindBean Grant grant);

    @SqlUpdate("DELETE FROM grants WHERE id = :id")
    int delete(@BindBean Grant grant);
}

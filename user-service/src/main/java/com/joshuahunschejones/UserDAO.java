package com.joshuahunschejones;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(UserRowMapper.class)
public interface UserDAO {

    @SqlQuery("SELECT * FROM users")
    List<User> findAll();

    @SqlQuery("SELECT * FROM users WHERE first_name LIKE :name OR last_name LIKE :name")
    List<User> findByName(@Bind("name") String name);

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    Optional<User> findById(@Bind("id") long id);

    @SqlUpdate("INSERT INTO users(first_name, last_name, email) VALUES(:firstName, :lastName, :email)")
    int create(@BindBean User user);

    @SqlUpdate("UPDATE users SET first_name = :firstName, last_name = :lastName, email = :email WHERE id = :id;")
    int updateById(@BindBean User user);

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    int deleteById(@Bind("id") long id);
}

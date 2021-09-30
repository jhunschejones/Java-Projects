package com.joshuahunschejones.springbootmvc.repository;

import com.joshuahunschejones.springbootmvc.beans.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select u from User as u where u.username = :username")
    public User searchByUsername(@Param("username") String username);
}

package com.joshuahunschejones.springbootmvc.repository;

import com.joshuahunschejones.springbootmvc.beans.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}

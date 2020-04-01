package com.joshuahunschejones.springbootmvc.repository;

import com.joshuahunschejones.springbootmvc.beans.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    // we get all the basic CRUD methods for free!

    // here is a custom database query method
    @Query("select p from Product p where p.name like %:name%")
    public List<Product> searchByName(@Param("name") String name);
}

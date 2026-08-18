package com.mh.productStore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByCategory(String category);
    List<Product> findByName(String name);
    List<Product> findByNameContainsIgnoreCase(String keyword);

    @Query("SELECT p FROM Product p WHERE p.price > :price")
    List<Product> getExpensiveProducts(@Param("price") double price);

    @Query("SELECT p FROM Product p WHERE p.price < :price")
    List<Product> getCheapProducts(@Param("price") double price);

    List<Product> findByCategoryAndPriceLessThan(String category, double price);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max")
    List <Product> findProductBetweenPrice(@Param("min") double min , @Param("max") double max);

}

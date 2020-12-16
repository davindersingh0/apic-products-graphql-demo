package com.walmart.stratiaf.graphql.apicproductsgraphqldemo.repository;

import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}

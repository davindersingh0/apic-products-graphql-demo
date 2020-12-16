package com.walmart.stratiaf.graphql.apicproductsgraphqldemo.service.datafetcher;

import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.model.Product;
import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.repository.ProductRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductsDataFetcher implements DataFetcher<List<Product>> {

    private final ProductRepository productRepository;

    public ProductsDataFetcher(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> get(DataFetchingEnvironment environment) throws Exception {
        return productRepository.findAll();
    }
}

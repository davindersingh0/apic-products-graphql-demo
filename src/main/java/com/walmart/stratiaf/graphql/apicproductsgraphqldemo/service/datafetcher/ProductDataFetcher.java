package com.walmart.stratiaf.graphql.apicproductsgraphqldemo.service.datafetcher;

import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.model.Product;
import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.repository.ProductRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDataFetcher implements DataFetcher<Product> {


    private ProductRepository productRepository;

    @Autowired
    ProductDataFetcher(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    @Override
    public Product get(DataFetchingEnvironment environment) throws Exception {
        String productId=environment.getArgument("id");

        return productRepository.findById(productId).get();
    }
}

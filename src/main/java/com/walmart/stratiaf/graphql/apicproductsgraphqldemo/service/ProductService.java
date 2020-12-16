package com.walmart.stratiaf.graphql.apicproductsgraphqldemo.service;

import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.model.Product;
import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.service.datafetcher.ProductDataFetcher;
import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.service.datafetcher.ProductsDataFetcher;
import graphql.ExecutionResult;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Deprecated
public class ProductService {

    private ProductsDataFetcher productsDataFetcher;
    private ProductDataFetcher productDataFetcher;

    @Autowired
    ProductService(ProductsDataFetcher productsDataFetcher, ProductDataFetcher productDataFetcher){

        this.productsDataFetcher=productsDataFetcher;
        this.productDataFetcher=productDataFetcher;

    }
    public Product getProductById(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {

        return this.productDataFetcher.get(dataFetchingEnvironment);


    }

    public List<Product> getAllProducts(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
        return this.productsDataFetcher.get(dataFetchingEnvironment);

    }
}

package com.walmart.stratiaf.graphql.apicproductsgraphqldemo.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.model.Product;
import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.repository.CustomResourceLoader;
import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.repository.ProductRepository;
import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.service.datafetcher.ProductDataFetcher;
import com.walmart.stratiaf.graphql.apicproductsgraphqldemo.service.datafetcher.ProductsDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class GraphQLProvider {

    private ProductRepository productRepository;
    private CustomResourceLoader customResourceLoader;
    private GraphQL graphQL;
    private ProductsDataFetcher productsDataFetcher;
    private ProductDataFetcher productDataFetcher;

    @Autowired
    public GraphQLProvider(CustomResourceLoader customResourceLoader, ProductsDataFetcher productsDataFetcher,
                           ProductDataFetcher productDataFetcher, ProductRepository productRepository) {
        this.customResourceLoader = customResourceLoader;
        this.productsDataFetcher=productsDataFetcher;
        this.productDataFetcher=productDataFetcher;
        this.productRepository=productRepository;
    }

    @Bean
    public GraphQL graphQL() {

        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        loadDataIntoHSQL();
        String sdl = customResourceLoader.getResourceData("classpath:products.graphql");

        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();

    }

    private GraphQLSchema buildSchema(String sdl) throws IOException {

        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring wiring = buildWiring();
        SchemaGenerator schemaGenerator= new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, wiring);

    }

    private RuntimeWiring buildWiring() {

        TypeRuntimeWiring.Builder queryTypeWiringBuilder = TypeRuntimeWiring.newTypeWiring("Query");

        queryTypeWiringBuilder.dataFetcher("allProducts", this.productsDataFetcher);
        queryTypeWiringBuilder.dataFetcher("product", this.productDataFetcher);

        return RuntimeWiring.newRuntimeWiring().type(queryTypeWiringBuilder).build();

    }


    private void loadDataIntoHSQL() throws IOException {

        loadProductsFromFile().forEach(Product -> {
            productRepository.save(Product);
        });

    }

    private List<Product> loadProductsFromFile() throws IOException {

        String productsAsjsonfiedStr = customResourceLoader.getResourceData("classpath:products.json");

        System.out.println("productsAsjsonfiedStr: "+productsAsjsonfiedStr);
        Type empMapType = new TypeToken<ArrayList<Product>>() {
        }.getType();

        List<Product> products = new Gson().fromJson(productsAsjsonfiedStr, empMapType);

        return products;
    }
}

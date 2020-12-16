package com.walmart.stratiaf.graphql.apicproductsgraphqldemo.repository;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Component
public class CustomResourceLoader implements ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String getResourceData(String resourceLocation) throws IOException {

        StringBuilder sb = new StringBuilder();

        Resource resourceContents = resourceLoader.getResource(resourceLocation);

        InputStream inputStream = resourceContents.getInputStream();

        if (inputStream == null) {
            throw new RuntimeException(resourceLocation + ": file not found in resource path");
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = bufferedReader.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return sb.toString();
    }

}

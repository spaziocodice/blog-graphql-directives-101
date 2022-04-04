package com.spaziocodice.graphql101.configuration;

import com.spaziocodice.graphql101.helpers.AuthHelper;
import graphql.spring.web.servlet.ExecutionInputCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;

@Configuration
public class GraphQLConfiguration {
    @Bean
    public ExecutionInputCustomizer executionInputCustomizer() {
        return (executionInput, webRequest) -> {
            executionInput.getGraphQLContext()
                // Here, the Authorization object that is put into the
                // GraphQLContext should come from a WebRequest's attribute:
                // <code>
                // .put("auth", webRequest.getAttribute("attribute name",
                //           RequestAttributes.SCOPE_REQUEST))</code>
                // However, for the sake of simplicity within our example,
                // we are using the AuthHelper class to provide that object.
                .put("auth", new AuthHelper("user1"));
            return CompletableFuture.completedFuture(executionInput);
        };
    }
}
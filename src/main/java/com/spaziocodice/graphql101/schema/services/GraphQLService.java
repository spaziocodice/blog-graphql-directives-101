package com.spaziocodice.graphql101.schema.services;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.spaziocodice.graphql101.schema.datafetchers.GraphQLDataFetchers;
import com.spaziocodice.graphql101.schema.directives.RequiredRoleDirective;
import com.spaziocodice.graphql101.schema.directives.HiddenDirective;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Service
public class GraphQLService {

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;


    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .directive("sc_required_role", new RequiredRoleDirective())
                .directive("sc_hidden", new HiddenDirective())
                .type(newTypeWiring("Query")
                        .dataFetcher("user", graphQLDataFetchers.user()))
                .type(newTypeWiring("Query")
                        .dataFetcher("employee", graphQLDataFetchers.employee()))
                .build();
    }
}

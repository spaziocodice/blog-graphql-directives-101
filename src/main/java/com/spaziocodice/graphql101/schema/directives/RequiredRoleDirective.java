package com.spaziocodice.graphql101.schema.directives;

import com.spaziocodice.graphql101.helpers.AuthHelper;
import graphql.GraphQLContext;
import graphql.GraphQLException;
import graphql.language.StringValue;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

import java.util.List;
import java.util.Optional;

public class RequiredRoleDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        var requiredAuthRole =
                Optional.of(
                    ((StringValue) environment.getDirective()
                        .getArgument("role")
                        .getArgumentValue()
                        .getValue()).getValue())
                .filter(role -> List.of("user", "administrator").contains(role))
                .map(String::valueOf)
                .orElseThrow(() -> new GraphQLException("Unknown role argument"));

        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();

        DataFetcher originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
        DataFetcher authDataFetcher = new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
                GraphQLContext contextMap = dataFetchingEnvironment.getGraphQlContext();
                AuthHelper authContext = contextMap.get("auth");

                if (authContext.userHasRole(requiredAuthRole)) {
                    return originalDataFetcher.get(dataFetchingEnvironment);
                } else {
                    return null;
                }
            }
        };

        environment.getCodeRegistry().dataFetcher(parentType, field, authDataFetcher);
        return field;
    }
}

package com.spaziocodice.graphql101.schema.directives;

import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public class HiddenDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(
            SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();

        DataFetcher<?> hiddenFieldDataFetcher = context -> null;

        environment.getCodeRegistry().dataFetcher(parentType, field, hiddenFieldDataFetcher);
        return field;
    }
}

package com.spaziocodice.graphql101.schema.datafetchers;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {

    private static final List<Map<String, String>> users = Arrays.asList(
            ImmutableMap.of(
                    "id", "1",
                    "username", "user1",
                    "email", "user1@mycompany.com",
                    "password", "Y6HhM|8?9,%X{w@dczg`",
                    "role", "user"),
            ImmutableMap.of(
                    "id", "2",
                    "username", "user2",
                    "email", "user2@mycompany.com",
                    "password", "_3KDuf5c6tgFGqmOueaF",
                    "role", "administrator")
    );

    private static final List<Map<String, Object>> employees = Arrays.asList(
            ImmutableMap.of("id", "12345",
                    "firstName", "Jean",
                    "lastName", "Kowalski",
                    "salary", 52000.00F),
            ImmutableMap.of("id", "23456",
                "firstName", "Martin",
                "lastName", "Brewert",
                "salary", 51000.00F),
            ImmutableMap.of("id", "34567",
                "firstName", "Monique",
                "lastName", "Duval",
                "salary", 54000.00F)
        );

    public DataFetcher user() {
        return dataFetchingEnvironment -> {
            String userId = dataFetchingEnvironment.getArgument("id");
            return users
                    .stream()
                    .filter(user -> user.get("id").equals(userId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher employee() {
        return dataFetchingEnvironment -> {
            String employeeId = dataFetchingEnvironment.getArgument("id");
            return employees
                    .stream()
                    .filter(employee -> employee.get("id").equals(employeeId))
                    .findFirst()
                    .orElse(null);
        };
    }
}

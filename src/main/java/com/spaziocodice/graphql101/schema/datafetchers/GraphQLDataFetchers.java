package com.spaziocodice.graphql101.schema.datafetchers;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Component
public class GraphQLDataFetchers {

    static final List<Map<String, String>> USERS = asList(
            Map.of(
                    "id", "1",
                    "username", "user1",
                    "email", "user1@mycompany.com",
                    "password", "Y6HhM|8?9,%X{w@dczg`",
                    "role", "user"),
            Map.of(
                    "id", "2",
                    "username", "user2",
                    "email", "user2@mycompany.com",
                    "password", "_3KDuf5c6tgFGqmOueaF",
                    "role", "administrator")
    );

    static final List<Map<String, Object>> EMPLOYEES = Arrays.asList(
            Map.of("id", "12345",
                    "firstName", "Jean",
                    "lastName", "Kowalski",
                    "salary", 52000.00F),
            Map.of("id", "23456",
                    "firstName", "Martin",
                    "lastName", "Brewert",
                    "salary", 51000.00F),
            Map.of("id", "34567",
                    "firstName", "Monique",
                    "lastName", "Duval",
                    "salary", 54000.00F)
    );

    public DataFetcher<Map<String, String>> user() {
        return dataFetchingEnvironment -> {
            var userId = dataFetchingEnvironment.getArgument("id");
            return USERS
                    .stream()
                    .filter(user -> user.get("id").equals(userId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<Map<String, Object>> employee() {
        return dataFetchingEnvironment -> {
            var employeeId = dataFetchingEnvironment.getArgument("id");
            return EMPLOYEES
                    .stream()
                    .filter(employee -> employee.get("id").equals(employeeId))
                    .findFirst()
                    .orElse(null);
        };
    }
}

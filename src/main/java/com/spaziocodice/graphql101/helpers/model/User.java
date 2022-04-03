package com.spaziocodice.graphql101.helpers.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
}

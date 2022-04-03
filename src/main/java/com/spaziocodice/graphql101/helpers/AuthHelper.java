package com.spaziocodice.graphql101.helpers;

import com.spaziocodice.graphql101.helpers.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthHelper {
    private User authenticatedUser;

    public boolean userHasRole(final String role) {
        return authenticatedUser.getRole().equals(role);
    }

    public AuthHelper(final String username) {
        switch (username) {
            case "user1":
                authenticatedUser = User.builder()
                        .username("user1")
                        .email("user1@mycompany.com")
                        .role("user").build();
                break;
            case "user2":
                authenticatedUser = User.builder()
                        .username("user2")
                        .email("user2@mycompany.com")
                        .role("administrator").build();
        }
    }
}

package dcu.fossilfuel.user.domain;

import lombok.Getter;

@Getter
public enum Role {

    ROLE_ADMIN("admin"),
    ROLE_USER("user");

    private final String description;

    Role(String description) {
        this.description = description;
    }

}




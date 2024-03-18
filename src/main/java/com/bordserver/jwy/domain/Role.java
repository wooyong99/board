package com.bordserver.jwy.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Role {
    @JsonProperty("USER")
    ROLE_USER("USER"),
    @JsonProperty("ADMIN")
    ROLE_ADMIN("ADMIN");

    private static final Map<String, Role> valueToName =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Role::getValue, Function.identity())));
    private final String value;

    Role(String value) {
        this.value = value;
    }

    public static Role getByValue(String value) {
        return valueToName.get(value);
    }
}

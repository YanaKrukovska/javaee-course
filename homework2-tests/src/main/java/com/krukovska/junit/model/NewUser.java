package com.krukovska.junit.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewUser {

    private final String login;
    private final String password;
    private final String fullName;

}

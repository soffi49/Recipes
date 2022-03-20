package com.recipes.backend.bizz.security;

import org.springframework.http.HttpHeaders;

public interface SecurityService {

    boolean isAuthenticated(HttpHeaders headers);
}

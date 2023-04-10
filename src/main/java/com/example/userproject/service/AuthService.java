package com.example.userproject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthService {

    @Value("${userAuthorized}")
    private String userAuthorized;
    @Value("${passAuthorized}")
    private String passAuthorized;

    public boolean isAuthenticated(String authHeader) {
        authHeader = authHeader.substring("Basic ".length());
        String decodedAuthHeader = new String(Base64.getDecoder().decode(authHeader));
        String decodedUser = decodedAuthHeader.substring(0,decodedAuthHeader.indexOf(":"));
        String decodedPass = decodedAuthHeader.substring(decodedAuthHeader.indexOf(":") + 1);
        return (decodedUser.equals(userAuthorized) && decodedPass.equals(passAuthorized));
    }
}

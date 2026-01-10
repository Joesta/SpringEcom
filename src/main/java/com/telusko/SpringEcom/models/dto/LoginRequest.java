package com.telusko.SpringEcom.models.dto;

import com.telusko.SpringEcom.exception.CredentialsRequiredException;

/**
 * @author Joesta
 */
public record LoginRequest (String username, String password) {}

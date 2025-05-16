package com.aithinkers.KeyCloakJwtAuth.entity;

public class AuthResponse {
    private String authToken;
    private String refreshToken;
    private Long expiresIn;
    private Long idleTimeout;

  public AuthResponse() {}

public AuthResponse(String authToken, String refreshToken, Long expiresIn, Long idleTimeout) {
	super();
	this.authToken = authToken;
	this.refreshToken = refreshToken;
	this.expiresIn = expiresIn;
	this.idleTimeout = idleTimeout;
}

public String getAuthToken() {
	return authToken;
}

public void setAuthToken(String authToken) {
	this.authToken = authToken;
}

public String getRefreshToken() {
	return refreshToken;
}

public void setRefreshToken(String refreshToken) {
	this.refreshToken = refreshToken;
}

public Long getExpiresIn() {
	return expiresIn;
}

public void setExpiresIn(Long expiresIn) {
	this.expiresIn = expiresIn;
}

public Long getIdleTimeout() {
	return idleTimeout;
}

public void setIdleTimeout(Long idleTimeout) {
	this.idleTimeout = idleTimeout;
}
  
  
}

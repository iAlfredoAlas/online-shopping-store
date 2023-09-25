package com.kodigo.shopping.online.store.security.utils;

import org.springframework.stereotype.Component;

@Component
public class JwtProperties {
	
	
	private String secret = "51c522109a71a0d958e17bb3b1658b65ed13d491801cab63181b376d8e4c847e";
	private Long lifetime = 1800000L;

	// Getters y setters

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Long getLifetime() {
		return lifetime;
	}

	public void setLifetime(Long lifetime) {
		this.lifetime = lifetime;
	}
}

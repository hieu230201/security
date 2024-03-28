package com.example.securitybase.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;

public class BodyToken {
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String sub;
	@JsonIgnoreProperties(ignoreUnknown = true)
	private Long iat;
	private Long exp;

	public BodyToken() {
	}

	public BodyToken(String sub, Long iat, Long exp) {
		super();
		this.sub = sub;
		this.iat = iat;
		this.exp = exp;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public Long getIat() {
		return iat;
	}

	public void setIat(Long iat) {
		this.iat = iat;
	}

	public Long getExp() {
		return exp;
	}

	public void setExp(Long exp) {
		this.exp = exp;
	}

	public static BodyToken loadMeFromJsonString(String json) {
		Assert.hasText(json, "Json input must be not null and not empty");
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(json, BodyToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
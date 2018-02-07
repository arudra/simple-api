package com.simple.lcbo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(as = ProductResponseImpl.class)
public interface ProductResponse
{

	@JsonProperty("status")
	int status();

	@JsonProperty("message")
	String message();

	@JsonProperty("result")
	List<DrinkResult> result();

}

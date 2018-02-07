package com.simple.lcbo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(as = DrinkResultImpl.class)
public interface DrinkResult
{

	@JsonProperty("id")
	long productId();

	@JsonProperty("name")
	String name();

	@JsonProperty("inventory_count")
	int inventoryCount();

}

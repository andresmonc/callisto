package com.jmoncayo.callisto.collection;

import com.jmoncayo.callisto.requests.ApiRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Collection {
	private String name;
	private List<ApiRequest> requests = new ArrayList<>();

	public Collection() {}

	public Collection(String name, List<ApiRequest> requests) {
		this.name = name;
		this.requests = requests;
	}
}

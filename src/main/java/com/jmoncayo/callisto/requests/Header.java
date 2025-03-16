package com.jmoncayo.callisto.requests;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Header {
    private String key;
    private String value;
    private String description;

}

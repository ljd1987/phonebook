package com.ljd.hackajob.phonebook.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneNumber {

    @NotNull
    private final String type;
    
    @NotNull
    @Pattern(regexp="\\d{1,32}")
    private String number;
    
    @JsonCreator
    public PhoneNumber(@JsonProperty("type") String type, @JsonProperty("number") String number) {
        this.type = type;
        this.number = number;
    }
    
    public String getType() {
        return type;
    }
    
    public String getNumber() {
        return number;
    }
}

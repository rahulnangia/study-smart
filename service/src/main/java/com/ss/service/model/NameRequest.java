package com.ss.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.validation.constraints.NotNull;

/**
 * Created by rahul on 12/2/15.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NameRequest {

    private String myName;

    @JsonIgnore
    private String myStatus;

    @NotNull(message = "lname cannot be null")
    private String lName;

}

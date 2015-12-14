package com.ss.common.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by rahul on 12/15/15.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {

    private String username;

    private String fname;

    private String lname;

    private String email;

    private boolean isActive;

    private School school;

}

package com.scm.arjun.scm20.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class RecaptchaResponse {

    private boolean success;
    private float score;
    private String action;
    @JsonAlias("error-codes")
    private List<String> errorCodes;

}

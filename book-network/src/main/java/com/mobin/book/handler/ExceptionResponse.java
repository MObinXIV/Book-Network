package com.mobin.book.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Map;
import java.util.Set;
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {
    private Integer businessErrorCode;
    private String businessErrorMessage;
    private String error;
    private Set<String> validationErrors;
    private Map<String,String> errors;


    public ExceptionResponse(Integer businessErrorCode, String businessErrorMessage, String error, Set<String> validationErrors, Map<String, String> errors) {
        this.businessErrorCode = businessErrorCode;
        this.businessErrorMessage = businessErrorMessage;
        this.error = error;
        this.validationErrors = validationErrors;
        this.errors = errors;
    }

    public ExceptionResponse() {
    }

    public Integer getBusinessErrorCode() {
        return businessErrorCode;
    }

    public void setBusinessErrorCode(Integer businessErrorCode) {
        this.businessErrorCode = businessErrorCode;
    }

    public String getBusinessErrorMessage() {
        return businessErrorMessage;
    }

    public void setBusinessErrorMessage(String businessErrorMessage) {
        this.businessErrorMessage = businessErrorMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Set<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Set<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}

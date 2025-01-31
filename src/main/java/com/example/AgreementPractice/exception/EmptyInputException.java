package com.example.AgreementPractice.exception;

public class EmptyInputException extends RuntimeException{
    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public EmptyInputException(String errorCode, String errorMessage)
    {
        super();
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }
}

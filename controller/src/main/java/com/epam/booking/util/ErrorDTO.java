package com.epam.booking.util;

public class ErrorDTO {
    private int codeStatus;
    private String errorMessage;
    private String exceptionMessage;

    public ErrorDTO(int codeStatus, String errorMessage, String exception) {
        this.codeStatus = codeStatus;
        this.errorMessage = errorMessage;
        this.exceptionMessage = exception;
    }

    public int getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(int codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}

package com.co.indra.coinmarketcap.portafolio.config;

public enum ErrorCodes {
    PORTAFOLIO_WITH_ID_EXISTS("Portafolio with that id already exists", "001"),

    PORTAFOLIO_NOT_FOUND("Portafolio not found", "002");

    String message;
    String code;

    ErrorCodes(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    }

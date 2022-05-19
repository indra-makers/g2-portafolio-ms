package com.co.indra.coinmarketcap.portafolio.config;

public enum ErrorCodes {
    PORTAFOLIO_WITH_ID_EXISTS("Portafolio with that id already exists", "001"),

    PORTAFOLIO_WITH_NAME_EXISTS("Portafolio with that name already exists", "001"),

    PORTAFOLIO_WITH_USERNAME_NOT_EXISTS("Portafolio with that username user not exists", "001"),

    PORTAFOLIO_NOT_FOUND("Portafolio not found", "002"),

    SYMBOL_COIN_NOT_FOUND("Symbol-coin not found", "003");

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

package com.co.indra.coinmarketcap.portafolio.config;

public enum ErrorCodes {
    PORTAFOLIO_WITH_ID_EXISTS("Portafolio with that id already exists", "001"),

    PORTAFOLIO_WITH_NAME_EXISTS("Portafolio with that name already exists", "002"),

    PORTAFOLIO_WITH_USERNAME_NOT_EXISTS("Portafolio with that username user not exists", "003"),

    PORTAFOLIO_NOT_FOUND("Portafolio not found", "004"),

    ASSET_ALREADY_EXISTS_IN_A_PORTAFOLIO("Asset already exists in a Portafolio", "005");

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

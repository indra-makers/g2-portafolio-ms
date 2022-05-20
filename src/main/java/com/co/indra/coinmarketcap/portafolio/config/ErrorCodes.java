package com.co.indra.coinmarketcap.portafolio.config;

public enum ErrorCodes {
    PORTAFOLIO_WITH_ID_EXISTS("Portafolio with that id already exists", "001"),

    PORTAFOLIO_WITH_NAME_EXISTS("Portafolio with that name already exists", "002"),

    PORTAFOLIO_WITH_USERNAME_NOT_EXISTS("Portafolio with that username user not exists", "003"),

    PORTAFOLIO_NOT_FOUND("Portafolio not found", "404"),

    ASSET_ALREADY_EXISTS_IN_A_PORTAFOLIO("Asset already exists in a Portafolio", "005"),

    ASSET_DOES_NOT_EXISTS("That asset doesnt exist", "006"),


    TRANSACTION_INVALID_QUANTITY("Quantity must  be greater than 0", "007"),

    ASSET_QUANTITY_DONT_SUPPORT_SELL("Cannot sell more than the current quantity", "008"),

    SYMBOL_COIN_NOT_FOUND("Symbol-coin not found", "009"),
    THE_PORTFOLIO_CANNOT_BE_DELETED_BECAUSE_IT_STILL_CONTAINS_ASSETS ("The Portafolio cannot be deleted because it still contains Assets", "010");


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

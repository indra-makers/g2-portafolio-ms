package com.co.indra.coinmarketcap.portafolio.exceptions;

import com.co.indra.coinmarketcap.portafolio.config.ErrorCodes;

public class NotFoundException extends RuntimeException{
    private ErrorCodes code;
    public NotFoundException(String message) {
        super(message);
    }

}

package com.co.indra.coinmarketcap.portafolio.exceptions;

import com.co.indra.coinmarketcap.portafolio.config.ErrorCodes;

public class BusinessException extends RuntimeException {
    private ErrorCodes code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ErrorCodes code) {
        super(code.getMessage());
        this.code = code;
    }

    public String getCode() {
        return code.getCode();
    }

}


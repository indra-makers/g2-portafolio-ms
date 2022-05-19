package com.co.indra.coinmarketcap.portafolio.config;

public class Routes {

    public static final String PORTAFOLIO_PATH = "/portafolios";

    public static final String PORTAFOLIO_BY_USER_PATH = "/users/{username}/portafolios";

    public static final String ASSETS_RESOURCE= "/assets";
    public static final String ADD_TRANSACTION_TO_ASSET ="/{idPortafolio}/assets/{idAssets}/transaction";

}

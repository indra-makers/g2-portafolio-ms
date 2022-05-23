package com.co.indra.coinmarketcap.portafolio.config;

public class Routes {

    public static final String PORTAFOLIO_PATH = "/portafolios";

    public static final String PORTAFOLIO_BY_USER_PATH = "/users/{username}/portafolios";

    public static final String ASSETS_RESOURCE= "/assets";
    public static final String ADD_TRANSACTION_TO_ASSET ="/{idPortafolio}/assets/{idAssets}/transaction";
    public static final String CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH = "/{idPortafolio}/assets";

    public static final String ID_PORTAFOLIO_PATH = "/{id_portafolio}";
    public static final String PORTAFOLIO_BY_SYMBOLCOIN_PATH = "/assets/{id_symbolcoin}";
    public static final String PORTAFOLIO_BY_NAME_PATH ="/name/{name_portafolio}";



}

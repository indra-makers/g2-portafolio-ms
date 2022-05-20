package com.co.indra.coinmarketcap.portafolio.controllers;

import com.co.indra.coinmarketcap.portafolio.config.Routes;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.model.responses.ErrorResponse;
import com.co.indra.coinmarketcap.portafolio.model.responses.PortafoliosDistributionResponse;
import com.co.indra.coinmarketcap.portafolio.repositories.AssetRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.PortafolioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PortafolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PortafolioRepository portafolioRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void addPortafolioHappyPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/portafolios")
                .content("{\n" +
                        "    \"username\": \"carolina\",\n" +
                        "    \"namePortafolio\": \"portafolio6\",\n" +
                        "    \"balancePortafolio\": \"40\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        List<Portafolio> portafolio = portafolioRepository.findByNamePortafolioAndUsername("portafolio6","carolina" );
        Assertions.assertEquals(1, portafolio.size());

        Portafolio portafolioToAssert = portafolio.get(0);

        Assertions.assertEquals("carolina", portafolioToAssert.getUsername());
        Assertions.assertEquals("portafolio6", portafolioToAssert.getNamePortafolio());
        Assertions.assertEquals(40, portafolioToAssert.getBalancePortafolio());
    }

    @Test
    public void addPortafolioPortafolioAlreadyExist() throws Exception {
        //----la preparacion de los datos de prueba-------
        portafolioRepository.create(new Portafolio("carolina","portafolio8", 50.0));

        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/portafolios")
                .content("{\n" +
                        "    \"username\": \"carolina\",\n" +
                        "    \"namePortafolio\": \"portafolio8\",\n" +
                        "    \"balancePortafolio\": \"50.0\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(412, response.getStatus());

        String textREsponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textREsponse, ErrorResponse.class);

        Assertions.assertEquals("002", error.getCode());
        Assertions.assertEquals("Portafolio with that name already exists", error.getMessage());

    }

    @Test
    @Sql("/testdata/get_portafolios_user.sql")
    public void getPortafoliosByUser() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.PORTAFOLIO_PATH+Routes.PORTAFOLIO_BY_USER_PATH, "carolina")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        Portafolio[] portafolios = objectMapper.readValue(response.getContentAsString(), Portafolio[].class);
        Assertions.assertEquals(4, portafolios.length);
    }

    @Test
    @Sql("/testdata/get_portafolios_user.sql")
    public void getPortafoliosWhenUserNotFound() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.PORTAFOLIO_PATH+Routes.PORTAFOLIO_BY_USER_PATH, "carolina2")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(404, response.getStatus());

        ErrorResponse error = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        Assertions.assertEquals("Portafolio with that username user not exists", error.getMessage());
    }

//--------------------------------------------------------------------------------------------------------------------------
    @Test
    @Sql("/testdata/get_portafolio.sql")
    public void createAssetInPortafolioHappyPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH, 100)
                .content("{\n" +
                        "    \"idSymbolCoin\": \"ASD\",\n" +
                        "    \"typeTransaction\": \"Compra\",\n" +
                        "    \"date\": \"2022-05-19\",\n" +
                        "    \"actualPrice\": \"40\",\n" +
                        "    \"fee\": \"45\",\n" +
                        "    \"notes\": \"...\",\n" +
                        "    \"amount\": \"50\",\n" +
                        "    \"quantity\": \"40\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_portafolio.sql")
    public void createAssetInPortafolioBadPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH, "gtr")
                .content("{\n" +
                        "    \"idSymbolCoin\": \"ASD\",\n" +
                        "    \"typeTransaction\": \"Compra\",\n" +
                        "    \"date\": \"2022-05-19\",\n" +
                        "    \"actualPrice\": \"40\",\n" +
                        "    \"fee\": \"45\",\n" +
                        "    \"notes\": \"...\",\n" +
                        "    \"amount\": \"50\",\n" +
                        "    \"quantity\": \"40\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_portafolio.sql")
    public void createAssetWithNonExistingPortafolioPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH, 99)
                .content("{\n" +
                        "    \"idSymbolCoin\": \"ASD\",\n" +
                        "    \"typeTransaction\": \"Compra\",\n" +
                        "    \"date\": \"2022-05-19\",\n" +
                        "    \"actualPrice\": \"40\",\n" +
                        "    \"fee\": \"45\",\n" +
                        "    \"notes\": \"...\",\n" +
                        "    \"amount\": \"50\",\n" +
                        "    \"quantity\": \"40\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());

        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);

        Assertions.assertEquals("404", error.getCode());
        Assertions.assertEquals("Portafolio not found", error.getMessage());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void createAssetWithExitingAssetInPortafolioPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH, 100)
                .content("{\n" +
                        "    \"idSymbolCoin\": \"XSA\",\n" +
                        "    \"typeTransaction\": \"Compra\",\n" +
                        "    \"date\": \"2022-05-19\",\n" +
                        "    \"actualPrice\": \"40\",\n" +
                        "    \"fee\": \"45\",\n" +
                        "    \"notes\": \"...\",\n" +
                        "    \"amount\": \"50\",\n" +
                        "    \"quantity\": \"40\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(412, response.getStatus());

        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);

        Assertions.assertEquals("005", error.getCode());
        Assertions.assertEquals("Asset already exists in a Portafolio", error.getMessage());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void createTransactionToAssetHappy() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.ADD_TRANSACTION_TO_ASSET, 100, 100)
                .content("{\n" +
                        "    \"typeTransaction\": \"buy\",\n" +
                        "    \"date\": \"2022-05-18\", \n" +
                        "    \"actualPrice\": 5000,\n" +
                        "    \"fee\": 3200,\n" +
                        "    \"notes\": \"cualquier cosa\",\n" +
                        "    \"quantity\": 2,\n" +
                        "    \"totalRecived\": 2,\n" +
                        "    \"amount\": 1\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
    }
    @Test
    @Sql("/testdata/get_assets.sql")
    public void createTransactionToAssetNotExist() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.ADD_TRANSACTION_TO_ASSET, 100, 200)
                .content("{\n" +
                        "    \"typeTransaction\": \"buy\",\n" +
                        "    \"date\": \"2022-05-18\", \n" +
                        "    \"actualPrice\": 5000,\n" +
                        "    \"fee\": 3200,\n" +
                        "    \"notes\": \"cualquier cosa\",\n" +
                        "    \"quantity\": 2,\n" +
                        "    \"totalRecived\": 5000,\n" +
                        "    \"amount\": 1\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());

        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);
        Assertions.assertEquals("404", error.getCode());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void createTransactionToAssetInPortfolioNotExist() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.ADD_TRANSACTION_TO_ASSET, 200, 100)
                .content("{\n" +
                        "    \"typeTransaction\": \"buy\",\n" +
                        "    \"date\": \"2022-05-18\", \n" +
                        "    \"actualPrice\": 5000,\n" +
                        "    \"fee\": 3200,\n" +
                        "    \"notes\": \"cualquier cosa\",\n" +
                        "    \"quantity\": 2,\n" +
                        "    \"totalRecived\": 5000,\n" +
                        "    \"amount\": 1\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());

        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);
        Assertions.assertEquals("404", error.getCode());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void createTransactionToAssetBadQuantity() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.ADD_TRANSACTION_TO_ASSET, 100, 100)
                .content("{\n" +
                        "    \"typeTransaction\": \"buy\",\n" +
                        "    \"date\": \"2022-05-18\", \n" +
                        "    \"actualPrice\": 5000,\n" +
                        "    \"fee\": 3200,\n" +
                        "    \"notes\": \"cualquier cosa\",\n" +
                        "    \"quantity\": 0,\n" +
                        "    \"totalRecived\": 0,\n" +
                        "    \"amount\": 1\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(412, response.getStatus());

        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);
        Assertions.assertEquals("007", error.getCode());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void createTransactionToAssetBadDate() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.ADD_TRANSACTION_TO_ASSET, 100, 100)
                .content("{\n" +
                        "    \"typeTransaction\": \"buy\",\n" +
                        "    \"date\": \"2027-05-20\", \n" +
                        "    \"actualPrice\": 5000,\n" +
                        "    \"fee\": 3200,\n" +
                        "    \"notes\": \"cualquier cosa\",\n" +
                        "    \"quantity\": 2,\n" +
                        "    \"totalRecived\": 2,\n" +
                        "    \"amount\": 1\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);



        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @Sql("/testdata/insert_portafolio_y_asset.sql")
    public void deleteAssetOfPortafolioHappyPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(Routes.PORTAFOLIO_PATH +Routes.ID_PORTAFOLIO_PATH + Routes.PORTAFOLIO_BY_SYMBOLCOIN_PATH, 111, "CRT");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        // ------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    public void deleteAssetofPortafolioIdportafolioNotFound() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(Routes.PORTAFOLIO_PATH +Routes.ID_PORTAFOLIO_PATH + Routes.PORTAFOLIO_BY_SYMBOLCOIN_PATH, 1111, "CRT");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        // ------------ las verificaciones--------------------
        Assertions.assertEquals(404, response.getStatus());

    }

    @Test
    public void deleteAssetWhereIdSymbolicoinlioNotFound() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(Routes.PORTAFOLIO_PATH +Routes.ID_PORTAFOLIO_PATH + Routes.PORTAFOLIO_BY_SYMBOLCOIN_PATH, 1111, "CRT");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        // ------------ las verificaciones--------------------
        Assertions.assertEquals(404, response.getStatus());

    }

    @Test
    @Sql("/testdata/get_portafolio_distribution.sql")
    public void getPortafolioDistribution() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.PORTAFOLIO_PATH + Routes.DISTRIBUTION_BY_IDPORTAFOLIO_PATH, 7)
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        PortafoliosDistributionResponse[] assets = objectMapper.readValue(response.getContentAsString(), PortafoliosDistributionResponse[].class);

        Assertions.assertEquals(2, assets[0].getAssets().size());

    }

    @Test
    @Sql("/testdata/get_portafolio_distribution.sql")
    public void getPortafolioDistributionIdPortafolioNotExist() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.PORTAFOLIO_PATH+Routes.DISTRIBUTION_BY_IDPORTAFOLIO_PATH, 10)
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(404, response.getStatus());

        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);

        Assertions.assertEquals("404", error.getCode());
        Assertions.assertEquals("Portafolio not found", error.getMessage());

    }


}

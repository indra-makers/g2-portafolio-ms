package com.co.indra.coinmarketcap.portafolio.controllers;

import com.co.indra.coinmarketcap.portafolio.config.Routes;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.model.entities.Transaction;
import com.co.indra.coinmarketcap.portafolio.API.model.UserApi;
import com.co.indra.coinmarketcap.portafolio.model.responses.ErrorResponse;
import com.co.indra.coinmarketcap.portafolio.model.responses.ListPortfolio;
import com.co.indra.coinmarketcap.portafolio.api.model.UserResponse;
import com.co.indra.coinmarketcap.portafolio.repositories.AssetRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.PortafolioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.co.indra.coinmarketcap.portafolio.model.responses.PortafoliosDistributionResponse;
import com.co.indra.coinmarketcap.portafolio.repositories.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
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
    private TransactionRepository transactionRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private RestTemplate restTemplate;

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
        Assertions.assertEquals("carolina", portafolios[0].getUsername());
        Assertions.assertEquals("carolina", portafolios[1].getUsername());
        Assertions.assertEquals("portafolio1", portafolios[0].getNamePortafolio());
        Assertions.assertEquals("portafolio2", portafolios[1].getNamePortafolio());
        Assertions.assertEquals(10, portafolios[0].getBalancePortafolio());
        Assertions.assertEquals(20, portafolios[1].getBalancePortafolio());
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
                        "    \"typeTransaction\": \"buy\",\n" +
                        "    \"date\": \"2022-05-25\",\n" +
                        "    \"actualPrice\": \"40\",\n" +
                        "    \"fee\": \"45\",\n" +
                        "    \"notes\": \"...\",\n" +
                        "    \"amount\": \"50\",\n" +
                        "    \"quantity\": \"40\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        List<Asset> lst = assetRepository.getIdAssetByPortafolioAndIdSymbolCoin("ASD", 100);
        int idAsset = Math.toIntExact(lst.get(0).getId());

        List<Transaction> transaction = transactionRepository.findTransactionByIdAsset(idAsset);
        Assertions.assertEquals(1, transaction.size());

        Transaction trasactionToAssert = transaction.get(0);

        Assertions.assertEquals("buy", trasactionToAssert.getTypeTransaction());
        Assertions.assertEquals(40, trasactionToAssert.getActualPrice());
        Assertions.assertEquals(45, trasactionToAssert.getFee());
        Assertions.assertEquals("...", trasactionToAssert.getNotes());
        Assertions.assertEquals(1600, trasactionToAssert.getAmount());
        Assertions.assertEquals(40, trasactionToAssert.getQuantity());
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
        List<Object[]> resulst = jdbcTemplate.query("SELECT id_assets, id_portafolio, id_symbolcoin, quantity, balance, dollar_balance FROM tbl_assets where id_portafolio = ?",
                (rs, rn) -> {
                    return new Object[] {rs.getObject("id_assets"), rs.getObject("id_portafolio"), rs.getObject("id_symbolcoin"), rs.getObject("quantity"), rs.getObject("balance"), rs.getObject("dollar_balance")};
                },
                111);

        Assertions.assertEquals(0, resulst.size());

    }

    @Test
    public void deleteAssetOfPortafolioIdportafolioNotFound() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(Routes.PORTAFOLIO_PATH +Routes.ID_PORTAFOLIO_PATH + Routes.PORTAFOLIO_BY_SYMBOLCOIN_PATH, 1111, "CRT");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        // ------------ las verificaciones--------------------
        Assertions.assertEquals(404, response.getStatus());

        ErrorResponse error = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        Assertions.assertEquals("Portafolio not found", error.getMessage());

    }

    @Test
    @Sql("/testdata/insert_portafolio_y_asset.sql")
    public void deleteAssetWhereIdSymbolicoinlioNotFound() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(Routes.PORTAFOLIO_PATH +Routes.ID_PORTAFOLIO_PATH + Routes.PORTAFOLIO_BY_SYMBOLCOIN_PATH, 111, "CVT");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        // ------------ las verificaciones--------------------
        Assertions.assertEquals(404, response.getStatus());

        ErrorResponse error = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        Assertions.assertEquals("Symbol-coin not found", error.getMessage());
    }

    @Test
    @Sql("/testdata/insert_portafolio_y_asset.sql")
    public void putNombrePortafolioHappyPath() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/portafolios/{id_portafolio}/name/{name_portafolio}", 111, "el nuevo nombre");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

        List<Object[]> resulst = jdbcTemplate.query("SELECT id_portafolio, name_portafolio FROM tbl_portafolios where id_portafolio = ?",
                (rs, rn) -> {
                    return new Object[] {rs.getObject("id_portafolio"), rs.getObject("name_portafolio")};
                },
                111);

        Assertions.assertEquals(1, resulst.size());
        Assertions.assertEquals("el nuevo nombre", String.valueOf(resulst.get(0)[1].toString()));
    }

    @Test
    @Sql("/testdata/insert_portafolio_y_asset.sql")
    public void putNombrePortafolioNotExist() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/portafolios/{id_portafolio}/name/{name_portafolio}", 1111, "portafolio Not Exist");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
        ErrorResponse error = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        Assertions.assertEquals("Portafolio not found", error.getMessage());

    }

    @Test
    @Sql("/testdata/get_portafolios_user.sql")
    public void getPortfoliosByUsernameHappyPath() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(Routes.PORTAFOLIO_PATH+Routes.ID_USER_PATH, "carolina");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        ListPortfolio[] portafolios = objectMapper.readValue(response.getContentAsString(), ListPortfolio[].class);
        Assertions.assertEquals(1, portafolios.length);
        Assertions.assertEquals(4, portafolios[0].getPortafolios().size());
        Assertions.assertEquals(160, portafolioRepository.getSumOfBalancePortfolios("carolina"));
    }

    @Test
    @Sql("/testdata/get_portafolios_user.sql")
    public void getPortfoliosByUsernameNotExists() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(Routes.PORTAFOLIO_PATH + Routes.ID_USER_PATH, "angie");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        Assertions.assertEquals(412, response.getStatus());
        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);
        Assertions.assertEquals("003", error.getCode());
    }
    @Sql("/testdata/get_portafolio.sql")
    public void deletePortafolioHappyPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Routes.PORTAFOLIO_PATH+Routes.ID_PORTAFOLIO_PATH, 100);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_portafolio.sql")
    public void deletePortafolioBadPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Routes.PORTAFOLIO_PATH+Routes.ID_PORTAFOLIO_PATH, "1fe");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_portafolio.sql")
    public void deletePortafolioWhenPortafolioNotExistPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Routes.PORTAFOLIO_PATH+Routes.ID_PORTAFOLIO_PATH, 1000);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());

        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);

        Assertions.assertEquals("404", error.getCode());
        Assertions.assertEquals("Portafolio not found", error.getMessage());


    }

    @Test
    @Sql("/testdata/insert_portafolio_y_asset.sql")
    public void deletePortafolioWhenThePortafolioHasAssetsPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Routes.PORTAFOLIO_PATH+Routes.ID_PORTAFOLIO_PATH, 111);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(412, response.getStatus());

        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);
        Assertions.assertEquals("010", error.getCode());
        Assertions.assertEquals("The Portafolio cannot be deleted because it still contains Assets", error.getMessage());
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

        Assertions.assertEquals(42.857142857142854, assets[0].getAssets().get(0).getPercent());
        Assertions.assertEquals("XSA", assets[0].getAssets().get(0).getIdSymbolCoin());
        Assertions.assertEquals(57.142857142857146, assets[0].getAssets().get(1).getPercent());
        Assertions.assertEquals("XOA", assets[0].getAssets().get(1).getIdSymbolCoin());
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

        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);
        Assertions.assertEquals("404", error.getCode());
        Assertions.assertEquals("Portafolio not found", error.getMessage());
    
    }
	
	@Test
    @Sql("/testdata/get_assets.sql")
    public void getAssetsByPortfolio() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(Routes.PORTAFOLIO_PATH+
                Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH,100).contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        Assertions.assertEquals(200,response.getStatus());
        Asset[] assets = objectMapper.readValue(response.getContentAsString(),Asset[].class);
        Assertions.assertEquals(1,assets.length);
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void getAssetsByPortfolioWhenPortfolioNotExists() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(Routes.PORTAFOLIO_PATH+
                Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH,45).contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        Assertions.assertEquals(404,response.getStatus());
        ErrorResponse error = objectMapper.readValue(response.getContentAsString(),ErrorResponse.class);
        Assertions.assertEquals("Portafolio not found",error.getMessage());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void getAssetsEmptyAssetsPortfolioExists() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(Routes.PORTAFOLIO_PATH+
                Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH,300).contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        Assertions.assertEquals(200,response.getStatus());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void getAssetsIdPortfolioBadParam() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(Routes.PORTAFOLIO_PATH+
                Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH,"xyz").contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        Assertions.assertEquals(500,response.getStatus());
    }

    @Test
    @Sql("/testdata/get_transaction_of_a_assets.sql")
    public void getTransactionOfAAssets() throws Exception{
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.PORTAFOLIO_PATH+Routes.ADD_TRANSACTION_TO_ASSET, 10,6)
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        Transaction[] transactions = objectMapper.readValue(response.getContentAsString(), Transaction[].class);

        Assertions.assertEquals(3, transactions[1].getId());
        Assertions.assertEquals(6, transactions[1].getIdAsset());
        Assertions.assertEquals("buy", transactions[1].getTypeTransaction());
        Assertions.assertEquals(7000.0, transactions[1].getActualPrice());
        Assertions.assertEquals(7, transactions[0].getId());
        Assertions.assertEquals(6, transactions[0].getIdAsset());
        Assertions.assertEquals("buy", transactions[0].getTypeTransaction());
        Assertions.assertEquals(6000.0, transactions[0].getActualPrice());
    }
    
    @Test
    @Sql("/testdata/insert_OneTransaction.sql")
    public void putTransactionHappyPath() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/portafolios/assets/transactions/{id_transaction}", 111)
                .content("{\n" +
                        "    \"date\": \"2022-09-05\",\n" +
                        "    \"actualPrice\": \"123\",\n" +
                        "    \"fee\": \"123\",\n" +
                        "    \"notes\": \"new notes\",\n" +
                        "    \"totalRecived\": \"123\",\n" +
                        "    \"amount\": \"123\",\n" +
                        "    \"quantity\": \"40\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        List<Object[]> resulst = jdbcTemplate.query("SELECT id_transaction, notes FROM tbl_transactions where id_transaction = ?",
                (rs, rn) -> {
                    return new Object[] {rs.getObject("id_transaction"), rs.getObject("notes")};
                },
                111);

        Assertions.assertEquals(1, resulst.size());
        Assertions.assertEquals("new notes", String.valueOf(resulst.get(0)[1].toString()));

    }

    @Test
    @Sql("/testdata/get_transaction_of_a_assets.sql")
    public void getTransactionOfAAssetsWhenIdAssetsNotExist() throws Exception{
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.PORTAFOLIO_PATH+Routes.ADD_TRANSACTION_TO_ASSET, 12,5)
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------

        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);
        Assertions.assertEquals("404", error.getCode());
        Assertions.assertEquals("That asset doesnt exist", error.getMessage());
    }
  
    @Sql("/testdata/insert_portafolio_y_asset.sql")
    public void putTransactionWhenNotExist() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/portafolios/assets/transactions/{id_transaction}", 12)
                .content("{\n" +
                        "    \"date\": \"2022-09-05\",\n" +
                        "    \"actualPrice\": \"123\",\n" +
                        "    \"fee\": \"123\",\n" +
                        "    \"notes\": \"new notes\",\n" +
                        "    \"totalRecived\": \"123\",\n" +
                        "    \"amount\": \"123\",\n" +
                        "    \"quantity\": \"40\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());

        ErrorResponse error = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        Assertions.assertEquals("Transaction not found", error.getMessage());
    }

    @Test
    public void addPortafolioWithUserNotExistPath() throws Exception {
        ResponseEntity<UserResponse> entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.<Class<UserResponse>>any())).thenReturn(entity);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/portafolios")
                .content("{\n" +
                        "    \"username\": \"user100\",\n" +
                        "    \"namePortafolio\": \"portafolio6\",\n" +
                        "    \"balancePortafolio\": \"40\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);
        Assertions.assertEquals("API EXTERNAL", error.getCode());
        Assertions.assertEquals("USER NOT FOUND", error.getMessage());
    }

    @Test
    public void addPortafolioWithUserHappyPath() throws Exception {
        UserApi mockedBody = new UserApi("user1","user1@gmail.com", "user1", 2);
        ResponseEntity<UserApi> entity = new ResponseEntity(mockedBody, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.<Class<UserApi>>any())).thenReturn(entity);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/portafolios")
                .content("{\n" +
                        "    \"username\": \"user1\",\n" +
                        "    \"namePortafolio\": \"portafolio6\",\n" +
                        "    \"balancePortafolio\": \"40\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
        List<Portafolio> portafolio = portafolioRepository.findByNamePortafolioAndUsername("portafolio6","user1" );
        Assertions.assertEquals(1, portafolio.size());
        Portafolio portafolioToAssert = portafolio.get(0);
        Assertions.assertEquals("user1", portafolioToAssert.getUsername());
        Assertions.assertEquals("portafolio6", portafolioToAssert.getNamePortafolio());
        Assertions.assertEquals(40, portafolioToAssert.getBalancePortafolio());
    }

    @Test
    @Sql("/testdata/get_portafolio.sql")
    public void addPortafolioToUserWhenPortafolioAlreadyExist() throws Exception {
        UserApi mockedBody = new UserApi("user1","user1000@gmail.com", "user1000", 2);
        ResponseEntity<UserApi> entity = new ResponseEntity(mockedBody, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.<Class<UserApi>>any())).thenReturn(entity);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/portafolios")
                .content("{\n" +
                        "    \"username\": \"user1\",\n" +
                        "    \"namePortafolio\": \"portafolio1\",\n" +
                        "    \"balancePortafolio\": \"100.0\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(412, response.getStatus());
        String textREsponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textREsponse, ErrorResponse.class);
        Assertions.assertEquals("002", error.getCode());
        Assertions.assertEquals("Portafolio with that name already exists", error.getMessage());

    }
}

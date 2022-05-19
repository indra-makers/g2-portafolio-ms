package com.co.indra.coinmarketcap.portafolio.controllers;

import com.co.indra.coinmarketcap.portafolio.config.Routes;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.model.responses.ErrorResponse;
import com.co.indra.coinmarketcap.portafolio.repositories.AssetRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.PortafolioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/testdata/get_portafolio.sql")
    public void createAssetHappyPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/assets/portafolios")
                .content("{\n" +
                        "    \"idPortafolio\": \"100\",\n" +
                        "    \"idSymbolCoin\": \"ASD\",\n" +
                        "    \"quantity\": \"40\",\n" +
                        "    \"balanceAsset\": \"45\",\n" +
                        "    \"dollarBalance\": \"50\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_portafolio.sql")
    public void createAssetBadPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/assets/portafolios")
                .content("{\n" +
                        "    \"idPortafolio\": \"gtr\",\n" +
                        "    \"idSymbolCoin\": \"ASD\",\n" +
                        "    \"quantity\": \"40\",\n" +
                        "    \"balanceAsset\": \"45\",\n" +
                        "    \"dollarBalance\": \"50\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_portafolio.sql")
    public void createAssetWithDoesNotExistPortafolioPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/assets/portafolios")
                .content("{\n" +
                        "    \"idPortafolio\": \"99\",\n" +
                        "    \"idSymbolCoin\": \"ASD\",\n" +
                        "    \"quantity\": \"40\",\n" +
                        "    \"balanceAsset\": \"45\",\n" +
                        "    \"dollarBalance\": \"50\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void createAssetWithPortafolioExitsPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/assets/portafolios")
                .content("{\n" +
                        "    \"idPortafolio\": \"100\",\n" +
                        "    \"idSymbolCoin\": \"XSA\",\n" +
                        "    \"quantity\": \"40\",\n" +
                        "    \"balanceAsset\": \"45\",\n" +
                        "    \"dollarBalance\": \"50\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(412, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void updateQuantityAssetToBuy() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.ADD_TRANSACTION_TO_ASSET, 100, 100)
                .content("{\n" +
                        "    \"typeTransaction\": \"buy\",\n" +
                        "    \"date\": \"2022-05-19\", \n" +
                        "    \"actualPrice\": 5000,\n" +
                        "    \"fee\": 3200,\n" +
                        "    \"notes\": \"cualquier cosa\",\n" +
                        "    \"quantity\": 2,\n" +
                        "    \"totalRecived\": 2,\n" +
                        "    \"amount\": 1\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
        List<Asset> assets = assetRepository.findAssetById(100l);
        Assertions.assertEquals(32, assets.get(0).getQuantity());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void updateQuantityAssetToSellSupport() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.ADD_TRANSACTION_TO_ASSET, 100, 100)
                .content("{\n" +
                        "    \"typeTransaction\": \"sell\",\n" +
                        "    \"date\": \"2022-05-19\", \n" +
                        "    \"actualPrice\": 5000,\n" +
                        "    \"fee\": 3200,\n" +
                        "    \"notes\": \"cualquier cosa\",\n" +
                        "    \"quantity\": 2,\n" +
                        "    \"totalRecived\": 2,\n" +
                        "    \"amount\": 1\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
        List<Asset> assets = assetRepository.findAssetById(100l);
        Assertions.assertEquals(28, assets.get(0).getQuantity());
    }

    @Test
    @Sql("/testdata/get_assets.sql")
    public void updateQuantityAssetToSellNotSupport() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.PORTAFOLIO_PATH+Routes.ADD_TRANSACTION_TO_ASSET, 100, 100)
                .content("{\n" +
                        "    \"typeTransaction\": \"sell\",\n" +
                        "    \"date\": \"2022-05-19\", \n" +
                        "    \"actualPrice\": 5000,\n" +
                        "    \"fee\": 3200,\n" +
                        "    \"notes\": \"cualquier cosa\",\n" +
                        "    \"quantity\": 31,\n" +
                        "    \"totalRecived\": 2,\n" +
                        "    \"amount\": 1\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(412, response.getStatus());
        String textResponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textResponse, ErrorResponse.class);
        Assertions.assertEquals("008", error.getCode());

    }
}

package com.co.indra.coinmarketcap.portafolio;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.RouteMatcher;

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

        Assertions.assertEquals("001", error.getCode());
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

        Portafolio[] measures = objectMapper.readValue(response.getContentAsString(), Portafolio[].class);
        Assertions.assertEquals(4, measures.length);
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

    @Test
    @Sql("/testdata/insert_portafolio_y_asset.sql")
    public void deleteCoinWatchlistHappyPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(Routes.PORTAFOLIO_PATH +Routes.ID_PORTAFOLIO_PATH + Routes.PORTAFOLIO_BY_SYMBOLCOIN_PATH, 111, "CRT");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        // ------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

    }

}

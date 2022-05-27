package com.co.indra.coinmarketcap.portafolio.controllers;

import com.co.indra.coinmarketcap.portafolio.config.Routes;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.History;
import com.co.indra.coinmarketcap.portafolio.repositories.HistoryAssetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class HistoryAssetsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HistoryAssetRepository historyAssetRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/testdata/insert_history.sql")
    public void getHistoryAssets() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(Routes.PORTAFOLIO_PATH+
                Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH,100).contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        Assertions.assertEquals(200,response.getStatus());
        History[] historyassets = objectMapper.readValue(response.getContentAsString(),History[].class);
        Assertions.assertEquals(1,historyassets.length);
    }


}

package com.co.indra.coinmarketcap.portafolio.services;

import com.co.indra.coinmarketcap.portafolio.model.entities.History;
import com.co.indra.coinmarketcap.portafolio.repositories.HistoryAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryAssetService {

    @Autowired
    HistoryAssetRepository historyAssetRepository;
}

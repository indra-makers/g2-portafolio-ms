package com.co.indra.coinmarketcap.portafolio.model.responses;

import java.io.Serializable;
import java.util.List;

public class PortafoliosDistributionResponse {

    private List<PortafoliosDistribution> assets;

    public PortafoliosDistributionResponse() {
    }

    public PortafoliosDistributionResponse(List<PortafoliosDistribution> assets) {
        this.assets = assets;
    }

    public List<PortafoliosDistribution> getAssets() {
        return assets;
    }

    public void setAssets(List<PortafoliosDistribution> assets) {
        this.assets = assets;
    }
}

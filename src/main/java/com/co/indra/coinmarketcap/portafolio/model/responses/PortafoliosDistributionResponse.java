package com.co.indra.coinmarketcap.portafolio.model.responses;

import java.io.Serializable;
import java.util.List;

public class PortafoliosDistributionResponse  implements Serializable {

    private List<PortafoliosDistribution> assets;

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

package com.cks.bogeom.service;

import com.cks.bogeom.domain.Market;
import com.cks.bogeom.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;

    @Transactional
    public void saveMarket(Market market) {
        marketRepository.save(market);
    }
    @Transactional
    public void updateMarket(Long marketId, String marketName, Long marketPrice){
        Market findMarket = marketRepository.findOne(marketId);
        //값 변경
        findMarket.setMarketName(marketName);
        findMarket.setMarketPrice(marketPrice);
    }

    public List<Market> findMarkets(){
        return marketRepository.findAll();
    }

    public Market findOne(Long marketId) {
        return marketRepository.findOne(marketId);
    }
}

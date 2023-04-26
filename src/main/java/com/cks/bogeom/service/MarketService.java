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
    public Long saveMarket(Market market){
        marketRepository.save(market);
        return market.getId();
    }
}

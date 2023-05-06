package com.cks.bogeom.service;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.Market;
import com.cks.bogeom.repository.ItemRepository;
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
    private final ItemRepository itemRepository;

//    @Transactional
//    public void saveMarket(Market market) {
//        // 마켓 저장
//        marketRepository.save(market);
//    }

    //테스트시에만 Long으로 바꿈
    @Transactional
    public Long saveMarket(Long itemId, String marketName, Long marketPrice) {
        //엔티티 조회
        Item item = itemRepository.findOne(itemId);

        //마켓 생성
        Market market = Market.createMarket(item, marketName, marketPrice);

        // 마켓 저장
        marketRepository.save(market);

        return market.getId();
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
        //마켓 엔티티 조회
        return marketRepository.findOne(marketId);
    }
}

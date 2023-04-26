package com.cks.bogeom.repository;

import com.cks.bogeom.domain.Market;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.error.Mark;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class MarketRepositoryTest {
    @Autowired private MarketRepository marketRepository;

    @Test
    @Transactional
    public void 마켓등록() throws Exception{
        //given
        Market market = new Market();
        market.setMarketName("쿠팡");
        //when
        Long saveName = marketRepository.save(market);
        //then
        assertEquals(market, marketRepository.findOne(saveName));
    }

}
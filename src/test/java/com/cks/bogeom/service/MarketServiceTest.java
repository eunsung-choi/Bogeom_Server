package com.cks.bogeom.service;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.Market;
import com.cks.bogeom.repository.ItemRepository;
import com.cks.bogeom.repository.MarketRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.cks.bogeom.domain.Market.createMarket;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class MarketServiceTest {

    @Autowired MarketService marketService;
    @Autowired MarketRepository marketRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired EntityManager em;

    @Test
    public void 마켓생성() throws Exception{
        //given
        Item item = createItem();

        //when
        Long marketId = marketService.saveMarket(item.getId(), "쿠팡",1000L,"ddd",2000L,3000,"dddd");

        //then
        Market getMarket = marketRepository.findOne(marketId);

        assertEquals(item, itemRepository.findOne(item.getId()));
    }

    private Item createItem(){
        Item item = new Item();
        item.setItemName("너구리");
        item.setItemImg("111");
        item.setDetailImg("1111");
        em.persist(item);
        return item;
    }

}
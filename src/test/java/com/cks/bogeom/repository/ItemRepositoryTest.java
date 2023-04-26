package com.cks.bogeom.repository;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.Market;
import com.cks.bogeom.domain.review.Food;
import com.cks.bogeom.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemRepositoryTest {
    @Autowired ItemRepository itemRepository;
    @Autowired ItemService itemService;
    @Autowired EntityManager em;

    @Test
    public void 아이템등록() throws Exception{
        //given
        Item item = new Item();
        item.setItemName("신라면");

        //when
        Long saveId = itemService.saveItem(item);

        //then
        assertEquals(item, itemRepository.findOne(saveId));
    }


//    private Food createFood(String reviewContent, int reviewDate, long reviewRate){
//        Food food = new Food();
//        food.setReviewContent(reviewContent);
//        food.setReviewDate(reviewDate);
//        food.setReviewRate(reviewRate);
//        em.persist(food);
//        return food;
//    }
//
//    private Market createMarket(){
//        Market market = new Market();
//        market.setMarketName("네이버");
//        market.setMarketPrice(1000);
//        em.persist(market);
//        return market;
//    }

}
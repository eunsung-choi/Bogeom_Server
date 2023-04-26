package com.cks.bogeom.repository;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.Market;
import com.cks.bogeom.domain.review.Food;
import com.cks.bogeom.service.ItemService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("item이 DB에 잘 저장 되는지 확인")
    public void saveItem() throws Exception{
        //given(테스트 위한 사전 조건 및 Mocking 할 영역)
        Item item = new Item();
        item.setItemName("신라면");

        //when(테스트를 할 영역)
        Long saveId = itemService.saveItem(item);

        //then(테스트 이후 예상 결과와 실제 결과를 확인하는 영역)
        assertEquals(item, itemRepository.findOne(saveId));
    }

    @Test
    public void 마켓등록() throws Exception{
        //given
        Market market = createMarket();
    }


//    private Food createFood(String reviewContent, int reviewDate, long reviewRate){
//        Food food = new Food();
//        food.setReviewContent(reviewContent);
//        food.setReviewDate(reviewDate);
//        food.setReviewRate(reviewRate);
//        em.persist(food);
//        return food;
//    }

    private Market createMarket(){
        Market market = new Market();
        market.setMarketName("네이버");
        market.setMarketPrice(1000);
        em.persist(market);
        return market;
    }

}
package com.cks.bogeom.entity;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.Market;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class MarketTest {
    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity() throws Exception{
        Item itemA = new Item("아이템1");
        Item itemB = new Item("아이템2");
        em.persist(itemA);
        em.persist(itemB);

        Market market1 = Market.createMarket(itemA,"market3",1023L,"dfsfd",1000L,3000,"dddd");
        Market market2 = Market.createMarket(itemB,"market4",10233L,"daafd",2000L,2000,"ddaa");
        em.persist(market1);
        em.persist(market2);

        em.flush(); //commit 날리는 거

        List<Market> markets = em.createQuery("select m from Market m", Market.class)
                .getResultList();
        List<Item> items = em.createQuery("select i from Item i", Item.class)
                .getResultList();

        // 출력
        for (Market market : markets) {
            System.out.println("market = " + market);
            System.out.println("-> market.item = " + market.getItem()); // 속한 팀 출력
        }

    }


}

package com.cks.bogeom.service;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.repository.ItemRepository;
import com.cks.bogeom.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class ReviewServiceTest {

    @Autowired ReviewService reviewService;
    @Autowired ReviewRepository reviewRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired EntityManager em;

    @Test
    public void 리뷰생성() throws Exception{
        //given
        Item item = createItem();

        //when
        Long reviewId = reviewService.makeReview(item.getId(), "맛있음", 5L);

        //then
        Review getReview = reviewRepository.findOne(reviewId);

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
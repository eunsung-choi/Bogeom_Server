package com.cks.bogeom.repository;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class reviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    EntityManager em;
    @Test
    public void 리뷰를아이디로찾기() throws Exception{
        //given
        Item item = new Item("걸작떡볶이");
        Review review = new Review();
        review.setItem(item);
        List<Review> reviewById = reviewRepository.findById(item.getId());

        //when
        System.out.println(reviewById);
        assertEquals(review.getItem().getItemName(), item.getItemName());


        //then


//        assertEquals(item, getItemR);
//        assertEquals(item, getItemS);
    }

}

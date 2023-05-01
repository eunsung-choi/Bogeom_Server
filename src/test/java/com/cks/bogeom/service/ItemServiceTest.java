package com.cks.bogeom.service;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.repository.ItemRepository;
import com.cks.bogeom.repository.ReviewRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemServiceTest {
    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;
    @Autowired
    ReviewRepository reviewRepository;

    public Item createItem(){
        Item item = new Item();
        item.setItemName("구구");
        item.setItemImg("111");
        item.setDetailImg("222");

        Long itemId = itemService.saveItem(item);
        List<Review> reviews = reviewRepository.findById(itemId);
        return Item.createItem(item.getItemName(),item.getItemImg(),item.getDetailImg());
    }

//    @Test
//    @DisplayName("item 저장 테스트")
//    public void saveItemTest(){
//        Item item = createItem();
//        Item saveItem = itemService.saveItem(item);
//    }



}

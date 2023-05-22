package com.cks.bogeom.service;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.repository.CategoryRepository;
import com.cks.bogeom.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class CategoryServiceTest {
    @Autowired CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired EntityManager em;

    @Test
    public void 카테고리생성() throws Exception{
        //given
//        Item item = new Item();
//        item.setItemName("너구리");

        //when
        Long itemId = categoryService.saveCategory("Daily");
        Long itemId2 = categoryService.saveCategory("Food");
        Long itemId3 = categoryService.saveCategory("Kitchen");

//        categoryService.saveItemCategory(1L, 1L);
//        //then
//        Item getItemR = itemRepository.findOne(itemId);
//        Item getItemS = itemService.findOne(itemId);

//        assertEquals(item, getItemR);
//        assertEquals(item, getItemS);
    }



}

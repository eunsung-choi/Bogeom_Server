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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.error.Mark;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class ItemRepositoryTest {
    @Autowired ItemRepository itemRepository;
    @Autowired ItemService itemService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("item이 DB에 잘 저장 되는지 확인")
    @Rollback(value = false)
    public void saveItem() throws Exception{
        //given(테스트 위한 사전 조건 및 Mocking 할 영역)
//        Item item = new Item();
//        item.setId(1L);

        //when(테스트를 할 영역)
        Long saveId = itemService.saveItem("카레", null, null, "222", "dd", "Food");

        //then(테스트 이후 예상 결과와 실제 결과를 확인하는 영역)
//        assertEquals(item, itemRepository.findOne(saveId));
    }

    @Test
    @DisplayName("item이 DB에 잘 저장 되는지 확인")
    @Rollback(value = false)
    public void selectItem() throws Exception{
        //given
        Item item = new Item();
        item.setItemName("너구리");
        item.setItemImg("1111");
        item.setDetailImg("2222");
        item.setReviewClassCode("123");
        item.setEnuriLink("http//www");

        //when
        List<Item> itemList = itemRepository.findAll();
        //then(테스트 이후 예상 결과와 실제 결과를 확인하는 영역)
        System.out.println(itemList.get(0));

    }

    @Test
    public void basicCRUD(){
        Item item1 = new Item("item3");
        Item item2 = new Item("item4");
        em.persist(item1);
        em.persist(item2);
//        itemRepository.save(item1);
//        itemRepository.save(item2);

        // 리스트 조회 검증
//        List<Item> all = itemRepository.findAll();
//        assertThat(all.size()).isEqualTo(2);
        List<Item> all = itemService.findItems();

        System.out.println("크기"+ all.size());
        for(Item item : all){
            System.out.println("log = " + item.getItemName());
        }

    }


}
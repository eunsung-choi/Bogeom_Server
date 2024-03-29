package com.cks.bogeom.service;

import com.cks.bogeom.domain.Category;
import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.Market;
import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.repository.ItemRepository;
import com.cks.bogeom.repository.MarketRepository;
import com.cks.bogeom.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.error.Mark;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void update(Long itemId, String itemName, String img, String dImg, String reviewClassCode, String enuriLink){
        Item item = itemRepository.findOne(itemId);
        item.setItemName(itemName);
        item.setItemImg(img);
        item.setDetailImg(dImg);
        item.setReviewClassCode(reviewClassCode);
        item.setEnuriLink(enuriLink);
    }

    @Transactional
    public Long saveItem(String itemName, String itemImg, String detailImg, String reviewClassCode, String enuriLink, String categoryName) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setItemImg(itemImg);
        item.setDetailImg(detailImg);
        item.setReviewClassCode(reviewClassCode);
        item.setEnuriLink(enuriLink);
        item.setCategoryName(categoryName);
        //repo에 저장
        itemRepository.save(item);
        return item.getId();
    }

    @Transactional
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
    /**
     * 상품등록
     */

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
    //item 이름 검색
    public Item findItemByName(String itemName) {return itemRepository.findOneByName(itemName);}

}

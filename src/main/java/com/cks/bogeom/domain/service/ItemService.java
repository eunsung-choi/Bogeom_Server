package com.cks.bogeom.domain.service;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.Market;
import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.repository.ItemRepository;
import com.cks.bogeom.repository.MarketRepository;
import com.cks.bogeom.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void update(Long itemId, String itemName, int price, String img, String dImg){
        Item item = itemRepository.findOne(itemId);
        item.setItemName(itemName);
        item.setPrice(price);
        item.setItemImg(img);
        item.setDetailImg(dImg);
    }
    //item 저장
    @Transactional
    public Long saveItem(Item item){
        itemRepository.save(item);
        return item.getId();
    }

    //item 검색
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    //item 이름 검색
    public List<Item> findItemByName(String name) {
        return itemRepository.findByName(name);
    }

    //item 전체 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}

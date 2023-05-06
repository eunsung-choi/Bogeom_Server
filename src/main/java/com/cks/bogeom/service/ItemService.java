package com.cks.bogeom.service;

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
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void update(Long itemId, String itemName, String img, String dImg){
        Item item = itemRepository.findOne(itemId);
        item.setItemName(itemName);
        item.setItemImg(img);
        item.setDetailImg(dImg);
    }

    @Transactional
    public Long saveItem(String itemName, String itemImg,String detailImg) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setItemImg(itemImg);
        item.setDetailImg(detailImg);
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
//    @Transactional
//    public Long Item(Long marketId, Long reviewId){
//        //엔티티 조회
//        Market market = marketRepository.findOne(marketId);
//        Review review = reviewRepository.findOne(reviewId);
//
//        Item item = Item.createItem(reviewList, markets);
//    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
    //item 이름 검색
    public List<Item> findItemByName(String name) {
        return itemRepository.findByName(name);
    }

}

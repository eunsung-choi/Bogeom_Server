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

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    private final MarketRepository marketRepository;

    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    @Transactional
    public void selctItem() {
        itemRepository.findAll();
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

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}

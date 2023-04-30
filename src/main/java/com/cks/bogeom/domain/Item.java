package com.cks.bogeom.domain;

import com.cks.bogeom.domain.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id") //pk 이름
    private Long id;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<MarketItem> marketItems = new ArrayList<>();

    private String itemName;
    private String itemImg;
    private String detailImg;

    //==연관관계 메서드==//

    public void addMarketItem(MarketItem marketItem) {
        marketItems.add(marketItem);
        marketItem.setItem(this);
    }

    //==생성 메서드==//
    public static Item createItem(MarketItem... marketItems) {
        Item item = new Item();
//        item.setItemName(); test에서 넣어줌
//        item.setItemImg();
//        item.setDetailImg();
        for(MarketItem marketItem : marketItems){
            item.addMarketItem(marketItem);
        }
        return item;
    }


}
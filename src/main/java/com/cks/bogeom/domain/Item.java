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
    private List<Market> markets = new ArrayList<>();

    private String itemName;
    private String itemImg;
    private String detailImg;
    private int price;

    //==생성 메서드==//
    public static Item createItem(String itemName, String itemImg, String detailImg, int price) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setItemImg(itemImg);
        item.setDetailImg(detailImg);
        item.setPrice(price);
        return item;
    }

//    //==연관관계 메서드==//
//
//    public void addReview(Review review) {
//        reviews.add(review);
//        review.setItem(this);
//    }
//
//    public void addMarket(Market market) {
//        markets.add(market);
//        market.setItem(this);
//    }
//

//    public static Item createItem(){
//        Item item = new Item();
//        return item;
//    }



}
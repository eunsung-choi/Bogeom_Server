package com.cks.bogeom.domain;

import com.cks.bogeom.domain.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private String detailImg;
    private String itemImg;
    @Column(length = 100)
    private String itemName;
//
//    public Item() {
//    }

    public Item(String itemName) {
        this.itemName = itemName;
    }

    //==연관관계 메서드==//
    public void addReview(Review review) {
        reviews.add(review);
        review.setItem(this);
    }

    //==생성 메서드==//
    public static Item createItem(List<Review> reviewList, String itemName, String itemImg, String detailImg) {
        Item item = new Item();
        for(Review review : reviewList){
            item.addReview(review);
        }
        item.setItemName(itemName);
        item.setItemImg(itemImg);
        item.setDetailImg(detailImg);

        return item;
    }

    public Item(){

    }
//
//    @Builder
//    public Item(Long id,String itemName,String itemImg, String detailImg){
//        this.id=id;
//        this.itemName=itemName;
//        this.itemImg=itemImg;
//        this.detailImg=detailImg;
//    }

//    //==연관관계 메서드==//
//    public void addReview(Review review) {
//        reviews.add(review);
//        review.setItem(this);
//    }
//
//    public void addMarket(Market market) {
//        markets.add(market);
//        market.setItem(this);
//    }




}
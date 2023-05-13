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

    @Column(length = 100)
    private String itemName;
    private String itemImg;
    private String detailImg;
    private String reviewClassCode; //리뷰 작성, 수정 시 필요
    private String enuriLink; //아이템 링크

    public Item(String itemName) {
        this.itemName = itemName;
    }

//    //==연관관계 메서드==//
//    public void addReview(Review review) {
//        reviews.add(review);
//        review.setItem(this);
//    }


    //==생성 메서드==//
//    public static Item createItem(String itemName, String itemImg, String detailImg) {
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setItemImg(itemImg);
//        item.setDetailImg(detailImg);
//
//        return item;
//    }

    public Item(){
    }

}
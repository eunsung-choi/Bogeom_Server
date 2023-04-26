package com.cks.bogeom.domain.review;

import com.cks.bogeom.domain.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속관계 부모
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id") //외래키
    private Item item;

    private String reviewContent; //리뷰 내용
    private int reviewDate; //리뷰 날짜
    private Long reviewRate; //리뷰 별점

//    //==연관관계 메서드==//
//    public void setItem(Item item){
//        this.item = item;
//        item.getReviews().add(this);
//    }
}

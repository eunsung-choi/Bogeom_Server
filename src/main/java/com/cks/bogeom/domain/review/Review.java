package com.cks.bogeom.domain.review;

import com.cks.bogeom.domain.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속관계 부모
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id") //외래키
    private Item item;

    private String reviewContent; //리뷰 내용
    private LocalDateTime reviewDate; //리뷰 날짜
    private Long reviewRate; //리뷰 별점

    //리뷰 속성
    private Long scent; //향
    private Long clean; //세정력
    private Long stimulation; //자극도

    private Long spicy;
    private Long amount;
    private Long taste;
    private Long sugar;

    private Long solidity;
    private Long afterFeel;



    //==생성 메서드==//
    public static Review createReview(Item item, String reviewContent, Long reviewRate, Long scent, Long clean, Long stimulation,
                                      Long spicy, Long amount, Long taste, Long sugar,
                                      Long solidity, Long afterFeel){
        Review review = new Review();
        review.setItem(item);
        review.setReviewContent(reviewContent);
        review.setReviewDate(LocalDateTime.now());
        review.setReviewRate(reviewRate);
        review.setScent(scent);
        review.setClean(clean);
        review.setStimulation(stimulation);
        review.setSpicy(spicy);
        review.setAmount(amount);
        review.setTaste(taste);
        review.setSugar(sugar);
        review.setSolidity(solidity);
        review.setAfterFeel(afterFeel);
        return review;
    }

    //==연관관계 메서드==//
    public void setItem(Item item){
        this.item = item;
        item.getReviews().add(this);
    }

    //==리뷰 삭제 메서드==//

}

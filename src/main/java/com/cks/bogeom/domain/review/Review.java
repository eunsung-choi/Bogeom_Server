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
@Getter @Setter @NoArgsConstructor
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

    //==생성 메서드==//
    public static Review createReview(Item item, String reviewContent, Long reviewRate){
        Review review = new Review();
        review.setItem(item);
        review.setReviewContent(reviewContent);
        review.setReviewDate(LocalDateTime.now()); //시간은 리뷰 생성 시간으로 설정
        review.setReviewRate(reviewRate);
        return review;
    }

}

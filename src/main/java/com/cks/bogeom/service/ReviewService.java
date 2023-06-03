package com.cks.bogeom.service;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.Market;
import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.repository.ItemRepository;
import com.cks.bogeom.repository.ReviewRepository;
import com.cks.bogeom.repository.ReviewSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void update(Long id, String content, Long rate){
        Review review = reviewRepository.findOne(id);
        review.setReviewContent(content);
        review.setReviewRate(rate);
    }

    @Transactional
    public Long makeReview(Long itemId, String content, Long rate, Long scent,
                           Long clean, Long stimulation, Long spicy, Long amount,
                           Long taste, Long sugar, Long solidity, Long afterFeel) { //리뷰 생성 후 저장

        //엔티티 조회
        Item item = itemRepository.findOne(itemId);

        //리뷰 생성
        Review review = Review.createReview(item, content, rate, scent, clean, stimulation, spicy,
                amount, taste, sugar, solidity, afterFeel);

        //리뷰 저장
        reviewRepository.save(review);

        return review.getId();
    }

    //리뷰 삭제 기능 만들기

    //리뷰 전체 조회
    public List<Review> findAllReviews(){
        return reviewRepository.findAll();
    }

    //리뷰 한개 조회
    public Review findOne(Long reviewId) {
        //마켓 엔티티 조회
        return reviewRepository.findOne(reviewId);
    }

    //itemId로 review 조회
    public List<Review> findReviewsByItemId(Long itemId) {
        return reviewRepository.findById(itemId);
    }

    public List<Review> findReviewsByItemName(String itemName) {return reviewRepository.findByName(itemName);}

    public Review findReviewByItemName(String itemName){ return reviewRepository.findOneByName(itemName);}


}

package com.cks.bogeom.domain.service;

import com.cks.bogeom.domain.Item;
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
    public Long makeReview(Long itemId, String content, Long rate) { //리뷰 생성 후 저장
        Item item = itemRepository.findOne(itemId);
        Review review = Review.createReview(item, content, rate);
        reviewRepository.save(review);
        return review.getId();
    }

    //리뷰 삭제 기능 만들기

    //리뷰 전체 조회
    public List<Review> findAllReviews(){
        return reviewRepository.findAll();
    }

    //리뷰 검색
    public List<Review> findReviews(ReviewSearch reviewSearch) {
        return reviewRepository.findAllByString(reviewSearch);
    }


}

package com.cks.bogeom.service;

import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 등록
     */
    @Transactional
    public Long join(Review review) {

        validateDuplicateReview(review); //중복 리뷰 검증
        reviewRepository.save(review);
        return review.getId();
    }
    private void validateDuplicateReview(Review review) {
        List<Review> findReviews = reviewRepository.findByName(review.getReviewContent());
        if (!findReviews.isEmpty()) {
            throw new IllegalStateException("이미 등록한 리뷰입니다.");
        }
    }
    //리뷰 전체 조회
    public List<Review> findMembers() {
        return reviewRepository.findAll();
    }

    public Review findOne(Long memberId) {
        return reviewRepository.findOne(memberId);
    }
}

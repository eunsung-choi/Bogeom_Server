package com.cks.bogeom.api;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    //==Review 저장 API==//
    @PostMapping("/api/reviews")
    public CreateReviewResponse saveReview(@RequestBody @Valid CreateReviewRequest request) {
        Long reviewId = reviewService.makeReview(request.getItemId(), request.reviewContent, request.getReviewRate());
        return new CreateReviewResponse(reviewId);
    }

    @Data
    static class CreateReviewRequest {
        private Long itemId;
        private String reviewContent;
        private Long reviewRate; //리뷰 별점
    }

    @Data
    static class CreateReviewResponse {
        private Long reviewId;

        public CreateReviewResponse(Long reviewId) {
            this.reviewId = reviewId;
        }
    }

    //==Review 수정 API==//
    @PutMapping("/api/reviews/{id}")
    public UpdateReviewResponse updateReview(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateReviewRequest request) {
        reviewService.update(id, request.getReviewContent(), request.getReviewRate());
        Review findReview = reviewService.findOne(id);
        return new UpdateReviewResponse(id, findReview.getReviewContent(), findReview.getReviewRate());
    }

    @Data
    static class UpdateReviewRequest {
        private String reviewContent;
        private Long reviewRate;
    }

    @Data
    @AllArgsConstructor
    static class UpdateReviewResponse {
        private Long reviewId;
        private String reviewContent;
        private Long reviewRate;
    }


    //==Review 조회 API==//
    @GetMapping("/api/reviews")
    public Result findAll(){
        List<Review> findReviews = reviewService.findAllReviews();
        List<ReviewDto> collect = findReviews.stream()
                .map(m -> new ReviewDto(m.getItem().getId(),m.getId(), m.getReviewContent(), m.getReviewDate(), m.getReviewRate()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class ReviewDto{
        private Long itemId;
        private Long reviewId;
        private String reviewContent; //리뷰 내용
        private LocalDateTime reviewDate; //리뷰 날짜
        private Long reviewRate; //리뷰 별점
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}

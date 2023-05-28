package com.cks.bogeom.api;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.service.ReviewService;
import com.fasterxml.jackson.annotation.JsonInclude;
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
        Long reviewId = reviewService.makeReview(request.getItemId(), request.getReviewContent(), request.getReviewRate(),
                request.getScent(), request.getClean(), request.getStimulation(), request.getSpicy(),
                request.getAmount(), request.getTaste(), request.getSugar(), request.getSolidity(), request.getAfterFeel());
        return new CreateReviewResponse(reviewId);
    }

    @Data
    static class CreateReviewRequest {
        private Long itemId;
        private String reviewContent;
        private Long reviewRate; //리뷰 별점

        private Long scent;
        private Long clean;
        private Long stimulation;

        private Long spicy;
        private Long amount;
        private Long taste;
        private Long sugar;

        private Long solidity;
        private Long afterFeel;
    }

    @Data
    static class CreateReviewResponse {
        private Long reviewId;

        public CreateReviewResponse(Long reviewId) {
            this.reviewId = reviewId;
        }
    }

    //==Review 수정 API==//
    @PutMapping("/api/reviews/put/{id}")
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
                .map(m -> new ReviewDto(m.getItem().getId(),m.getId(), m.getReviewContent(), m.getReviewDate(), m.getReviewRate(),
                        m.getScent(), m.getClean(), m.getStimulation(), m.getSpicy(),
                        m.getAmount(), m.getTaste(), m.getSugar(), m.getSolidity(), m.getAfterFeel()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    //==Review itemId 조회 API==//
    @GetMapping("/api/reviews/{itemId}")
    public ItemReviewResult findByItemId(@PathVariable("itemId") Long itemId){
        List<Review> findReviews = reviewService.findReviewsByItemId(itemId);
        List<ReviewDto> collect = findReviews.stream()
                .map(m -> new ReviewDto(m.getItem().getId(),m.getId(), m.getReviewContent(), m.getReviewDate(), m.getReviewRate(),
                        m.getScent(), m.getClean(), m.getStimulation(), m.getSpicy(),
                        m.getAmount(), m.getTaste(), m.getSugar(), m.getSolidity(), m.getAfterFeel()))
                .collect(Collectors.toList());
        return new ItemReviewResult(itemId, collect);
    }

    @Data
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class ReviewDto{
        private Long itemId;
        private Long reviewId;
        private String reviewContent; //리뷰 내용
        private LocalDateTime reviewDate; //리뷰 날짜
        private Long reviewRate; //리뷰 별점

        private Long scent;
        private Long clean;
        private Long stimulation;

        private Long spicy;
        private Long amount;
        private Long taste;
        private Long sugar;

        private Long solidity;
        private Long afterFeel;
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class ItemReviewResult<T>{
        private T itemId;
        private T itemReviews;
    }
}

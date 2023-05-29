package com.cks.bogeom.api;

import com.cks.bogeom.domain.Category;
import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.Market;
import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.service.ItemService;
import com.cks.bogeom.service.MarketService;
import com.cks.bogeom.service.ReviewService;
import com.mysql.cj.log.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemApiController {
    private final ItemService itemService;
    private final ReviewService reviewService;
    private final MarketService marketService;

    //==Item 저장 API==//
    @PostMapping("/api/items")
    public CreateItemResponse saveItem(@RequestBody @Valid CreateItemRequest request) {
        Long itemId = itemService.saveItem(request.getItemName(), request.getItemImg(), request.getDetailImg(), request.getReviewClassCode(), request.getEnuriLink(), request.getCategoryName());
        return new CreateItemResponse(itemId);
    }


    @Data
    static class CreateItemRequest{
        private String itemName;
        private String itemImg;
        private String detailImg;
        private String reviewClassCode;
        private String enuriLink;
        private String categoryName;
    }

    @Data
    static class CreateItemResponse{
        private Long itemId;

        public CreateItemResponse(Long itemId) {
            this.itemId = itemId;
        }
    }

    //==Item 수정 API==//
    @PutMapping("/api/items/{id}")
    public UpdateItemResponse updateItem(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateItemRequest request){
        itemService.update(id, request.getItemName(), request.getItemImg(), request.getDetailImg(), request.getReviewClassCode(), request.getEnuriLink());
        Item findItem = itemService.findOne(id);
        return new UpdateItemResponse(id, findItem.getItemName(), findItem.getItemImg(), findItem.getDetailImg(), findItem.getReviewClassCode(), findItem.getEnuriLink());
    }

    @Data
    static class UpdateItemRequest{
        private String itemName;
        private String itemImg;
        private String detailImg;
        private String reviewClassCode;
        private String enuriLink;
    }

    @Data
    @AllArgsConstructor
    static class UpdateItemResponse{
        private Long itemId;
        private String itemName;
        private String itemImg;
        private String detailImg;
        private String reviewClassCode;
        private String enuriLink;
    }

    //==Item 전체 조회 API==//
    @GetMapping("/api/items")
    public Result findAll(){
        List<Item> findItems = itemService.findItems();

        List<ItemDto> collect = findItems.stream()
                .map(i -> new ItemDto(i.getId(), i.getItemName(), i.getItemImg(), i.getDetailImg(), i.getReviewClassCode(), i.getEnuriLink(), i.getCategoryName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class ItemDto{
        private Long itemId;
        private String itemName;
        private String itemImg;
        private String detailImg;
        private String reviewClassCode;
        private String enuriLink;
        private String categoryName;
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

//    //==Item 단품 조회 API==//
//    @GetMapping("/api/items/{id}")
//    public OneItemResult findOne( @PathVariable("id") Long id){
//        //itemInfo 부분
//        Item findItem = itemService.findOne(id);
//        OneItemDto oneItemDto = new OneItemDto(findItem.getId(), findItem.getItemName(), findItem.getItemImg(), findItem.getDetailImg());
//
//        //item_online_price 부분
//        List<Market> findMarkets = marketService.findMarketsByItemId(id); //itemId로 관련 market 찾기
//        List<OneMarketDto> itemMarketDto = findMarkets.stream()
//                .map(m -> new OneMarketDto(m.getMarketName(), m.getMarketCode(), m.getMarketLogo(), m.getMarketPrice(), m.getMarketDeliverFee(), m.getMarketLink()))
//                .collect(Collectors.toList());
//
//        //itemReview 부분
//        //Review들을 불러와서 값을 업데이트하는 과정
//        long totalScent=0, totalClean=0, totalStimulation=0, totalSpicy=0, totalAmount=0, totalTaste=0, totalSugar=0, totalSolidity=0, totalAfterFeel=0;
//        long totalReviewRate=0;
//        List<Review> findReviews = reviewService.findReviewsByItemId(id);
//        String categoryName = findItem.getCategoryName();
//        System.out.println("categoryName : "+categoryName);
//        System.out.println("findReviews : "+findReviews.stream().map(r -> r.getReviewContent()).collect(Collectors.toList()));
//
//        for (Review review : findReviews) { //총 합 계산
//            System.out.println(review.getReviewContent());
//            totalReviewRate += review.getReviewRate();
//                totalScent += (review.getScent() != null) ? review.getScent() : 0;
//                totalClean += (review.getClean() != null) ? review.getClean() : 0;
//                totalStimulation += (review.getStimulation() != null) ? review.getStimulation() : 0;
//                totalSpicy += (review.getSpicy() != null) ? review.getSpicy() : 0;
//                totalAmount += (review.getAmount() != null) ? review.getAmount() : 0;
//                totalTaste += (review.getTaste() != null) ? review.getTaste() : 0;
//                totalSugar += (review.getSugar() != null) ? review.getSugar() : 0;
//                totalSolidity += (review.getSolidity() != null) ? review.getSolidity() : 0;
//                totalAfterFeel += (review.getAfterFeel() != null) ? review.getAfterFeel() : 0;
//        }
//        //평균 구하기
//        int n = findReviews.size();
//        if(n!=0){
//            totalReviewRate /= n;
//            totalScent /= n;
//            totalClean /= n;
//            totalStimulation /= n;
//            totalSpicy /= n;
//            totalAmount /= n;
//            totalTaste /= n;
//            totalSugar /= n;
//            totalSolidity /= n;
//            totalAfterFeel /= n;
//        }
//
//        //dto 만들기
//        ReviewItemDto reviewItemDto = new ReviewItemDto(totalReviewRate, totalScent, totalClean, totalStimulation,totalSpicy, totalAmount, totalTaste, totalSugar,totalSolidity, totalAfterFeel);
//            return new OneItemResult(oneItemDto, reviewItemDto, itemMarketDto);
//    }

    //==Item 단품 조회 API==//
    @GetMapping("/api/items/{itemName}")
    public OneItemResult findOneByName( @PathVariable("itemName") String itemName){
        //itemInfo 부분
        Item findItem = itemService.findItemByName(itemName);
        Long id = findItem.getId();

        OneItemDto oneItemDto = new OneItemDto(findItem.getId(), findItem.getItemName(), findItem.getItemImg(), findItem.getDetailImg());

        //item_online_price 부분
        List<Market> findMarkets = marketService.findMarketsByItemId(id); //itemId로 관련 market 찾기
        List<OneMarketDto> itemMarketDto = findMarkets.stream()
                .map(m -> new OneMarketDto(m.getMarketName(), m.getMarketCode(), m.getMarketLogo(), m.getMarketPrice(), m.getMarketDeliverFee(), m.getMarketLink()))
                .collect(Collectors.toList());

        //itemReview 부분
        //Review들을 불러와서 값을 업데이트하는 과정
        long totalScent=0, totalClean=0, totalStimulation=0, totalSpicy=0, totalAmount=0, totalTaste=0, totalSugar=0, totalSolidity=0, totalAfterFeel=0;
        long totalReviewRate=0;
        List<Review> findReviews = reviewService.findReviewsByItemId(id);
        String categoryName = findItem.getCategoryName();
        System.out.println("categoryName : "+categoryName);
        System.out.println("findReviews : "+findReviews.stream().map(r -> r.getReviewContent()).collect(Collectors.toList()));

        for (Review review : findReviews) { //총 합 계산
            System.out.println(review.getReviewContent());
            totalReviewRate += review.getReviewRate();
            totalScent += (review.getScent() != null) ? review.getScent() : 0;
            totalClean += (review.getClean() != null) ? review.getClean() : 0;
            totalStimulation += (review.getStimulation() != null) ? review.getStimulation() : 0;
            totalSpicy += (review.getSpicy() != null) ? review.getSpicy() : 0;
            totalAmount += (review.getAmount() != null) ? review.getAmount() : 0;
            totalTaste += (review.getTaste() != null) ? review.getTaste() : 0;
            totalSugar += (review.getSugar() != null) ? review.getSugar() : 0;
            totalSolidity += (review.getSolidity() != null) ? review.getSolidity() : 0;
            totalAfterFeel += (review.getAfterFeel() != null) ? review.getAfterFeel() : 0;
        }
        //평균 구하기
        int n = findReviews.size();
        if(n!=0){
            totalReviewRate /= n;
            totalScent /= n;
            totalClean /= n;
            totalStimulation /= n;
            totalSpicy /= n;
            totalAmount /= n;
            totalTaste /= n;
            totalSugar /= n;
            totalSolidity /= n;
            totalAfterFeel /= n;
        }

        //dto 만들기
        ReviewItemDto reviewItemDto = new ReviewItemDto(totalReviewRate, totalScent, totalClean, totalStimulation,totalSpicy, totalAmount, totalTaste, totalSugar,totalSolidity, totalAfterFeel);
        return new OneItemResult(oneItemDto, reviewItemDto, itemMarketDto);
    }

    @Data
    @AllArgsConstructor
    static class OneItemDto{
        private Long itemId;
        private String itemName;
        private String itemImg;
        private String detailImg;

    }
    @Data
    @AllArgsConstructor
    static class ReviewItemDto{
        private Long reviewRate;
        private Long totalScent;
        private Long totalClean;
        private Long totalStimulation;
        private Long totalSpicy;
        private Long totalAmount;
        private Long totalTaste;
        private Long totalSugar;
        private Long totalSolidity;
        private Long totalAfterFeel;
    }


    //    @Data
//    @AllArgsConstructor
//    static class DailyDto{
//        private Long reviewRate;
//        private Long totalScent;
//        private Long totalClean;
//        private Long totalStimulation;
//
//    }
//    @Data
//    @AllArgsConstructor
//    static class FoodDto{
//        private Long reviewRate;
//        private Long totalSpicy;
//        private Long totalAmount;
//        private Long totalTaste;
//        private Long totalSugar;
//    }
//    @Data
//    @AllArgsConstructor
//    static class KitchenDto{
//        private Long reviewRate;
//        private Long totalSolidity;
//        private Long totalAfterFeel;
//    }
    @Data
    @AllArgsConstructor
    static class OneMarketDto{
        private String marketName;
        private Long marketCode;
        private String marketLogo;
        private Long marketPrice;
        private int DeliverFee;
        private String marketLink;
    }

    @Data
    @AllArgsConstructor
    static class OneItemResult<T>{
        private T itemInfo;
        private T itemReview;
        private T itemOnlinePrice;
    }
}

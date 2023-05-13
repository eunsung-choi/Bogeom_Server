package com.cks.bogeom.api;

import com.cks.bogeom.domain.Market;
import com.cks.bogeom.service.MarketService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MarketApiController {
    private final MarketService marketService;

    //==Market 저장 API==//
    @PostMapping("/api/markets")
    public CreateMarketResponse saveMarket(@RequestBody @Valid CreateMarketRequest request) {
        Long marketId = marketService.saveMarket(request.getItemId(), request.getName(), request.getCode(), request.getLogo(), request.getPrice(), request.getDeliverFee(), request.getLink());
        return new CreateMarketResponse(marketId);
    }

    @Data
    static class CreateMarketRequest {
        private Long itemId;
        private String name;
        private Long code;
        private String logo;
        private Long price;
        private int deliverFee;
        private String link;

        public CreateMarketRequest(Long itemId, String name, Long code, String logo, Long price, int deliverFee, String link) {
            this.itemId = itemId;
            this.name = name;
            this.code = code;
            this.logo = logo;
            this.price = price;
            this.deliverFee = deliverFee;
            this.link = link;
        }
    }

    @Data
    static class CreateMarketResponse {
        private Long marketId;

        public CreateMarketResponse(Long marketId) {
            this.marketId = marketId;
        }
    }

    //==Market 수정 API==//
    @PutMapping("/api/markets/{id}")
    public UpdateMarketResponse updateMarket(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMarketRequest request) {
        marketService.updateMarket(id, request.getName(), request.getCode(), request.getLogo(), request.getPrice(), request.getDeliverFee(), request.getLink());
        Market findMarket = marketService.findOne(id);
        return new UpdateMarketResponse(id, findMarket.getMarketName(), findMarket.getMarketCode(), findMarket.getMarketLogo(), findMarket.getMarketPrice(), findMarket.getMarketDeliverFee(), findMarket.getMarketLink());
    }

    @Data
    static class UpdateMarketRequest {
        private String name;
        private Long code;
        private String logo;
        private Long price;
        private int deliverFee;
        private String link;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMarketResponse {
        private Long marketId;
        private String name;
        private Long code;
        private String logo;
        private Long price;
        private int DeliverFee;
        private String link;
    }


    //==Market 조회 API==//
    @GetMapping("/api/markets")
    public Result findAll(){
        List<Market> findMarkets = marketService.findMarkets();
        List<MarketDto> collect = findMarkets.stream()
                .map(m -> new MarketDto(m.getMarketName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class MarketDto{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}

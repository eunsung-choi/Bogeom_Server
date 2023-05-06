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
        Long marketId = marketService.saveMarket(request.getItemId(), request.getName(), request.getPrice());
        return new CreateMarketResponse(marketId);
    }

    @Data
    static class CreateMarketRequest {
        private Long itemId;
        private String name;
        private Long price;

        public CreateMarketRequest(Long itemId, String name, Long price) {
            this.itemId = itemId;
            this.name = name;
            this.price = price;
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
        marketService.updateMarket(id, request.getName(), request.getPrice());
        Market findMarket = marketService.findOne(id);
        return new UpdateMarketResponse(id, findMarket.getMarketName(), findMarket.getMarketPrice());
    }

    @Data
    static class UpdateMarketRequest {
        private String name;
        private Long price;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMarketResponse {
        private Long marketId;
        private String name;
        private Long price;
    }


    //==Market 조회 API==//
    @GetMapping("/api/markets")
    public Result findAllMarket(){
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

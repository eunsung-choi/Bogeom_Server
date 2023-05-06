package com.cks.bogeom.api;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemApiController {
    private final ItemService itemService;

    //==Item 저장 API==//
    @PostMapping("/api/items")
    public CreateItemResponse saveItem(@RequestBody @Valid CreateItemRequest request) {
        Long itemId = itemService.saveItem(request.getItemName(), request.getItemImg(), request.getDetailImg());
        return new CreateItemResponse(itemId);
    }


    @Data
    static class CreateItemRequest{
        private String itemName;
        private String itemImg;
        private String detailImg;
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
        itemService.update(id, request.getItemName(), request.getItemImg(), request.getDetailImg());
        Item findItem = itemService.findOne(id);
        return new UpdateItemResponse(id, findItem.getItemName(), findItem.getItemImg(), findItem.getDetailImg());
    }

    @Data
    static class UpdateItemRequest{
        private String itemName;
        private String itemImg;
        private String detailImg;
    }

    @Data
    @AllArgsConstructor
    static class UpdateItemResponse{
        private Long itemId;
        private String itemName;
        private String itemImg;
        private String detailImg;
    }

    //==Item 조회 API==//
    @GetMapping("/api/items")
    public Result findAll(){
        List<Item> findItems = itemService.findItems();
        List<ItemDto> collect = findItems.stream()
                .map(i -> new ItemDto(i.getId(), i.getItemName(), i.getItemImg(), i.getDetailImg()))
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

    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}

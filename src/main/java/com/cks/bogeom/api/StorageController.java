package com.cks.bogeom.api;

import com.cks.bogeom.api.ItemApiController.OneItemResult;
import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.Market;
import com.cks.bogeom.domain.review.Review;
import com.cks.bogeom.service.ItemService;
import com.cks.bogeom.service.MarketService;
import com.cks.bogeom.service.ReviewService;
import com.cks.bogeom.service.StorageService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StorageController {

    private final ItemService itemService;
    private final ReviewService reviewService;
    private final MarketService marketService;
    final private StorageService storageService;

    // application.yml 파일에서 AI 서버 URL 읽어오기
    @Value("${ai.server.url}")
    private String aiServerUrl;


//    // Upload image
//    @PostMapping("/image")
//    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
//        String uploadImage = storageService.uploadImage(file); // Save the original file name
//
//        String url = "http://localhost:8080/api/image/" + file.getOriginalFilename();
//
//        // Trigger the downloadImage method
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.TEXT_PLAIN)
//                .body(url);
//    }
    private static final RestTemplate REST_TEMPLATE;


    static {
        // RestTemplate 기본 설정을 위한 Factory 생성
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(16000);
        factory.setReadTimeout(16000);
//        factory.setBufferRequestBody(false);
        REST_TEMPLATE = new RestTemplate(factory);
    }

    //ai 서버에 이미지 전달하는 API, /api/proxyUpload
    @PostMapping("/proxyUpload")
    public OneItemResult uploadImages(@RequestParam("image") MultipartFile image) throws IOException {
        JsonNode response;
        HttpStatus httpStatus = HttpStatus.CREATED;

        try {
            LinkedMultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
            ByteArrayResource resource = new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            };
            //{"image":이미지파일}을 map에 추가한다.
            data.add("image", resource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA); //폼 데이터로 보낸다.

            //요청 entity 만들기
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(data, headers);
            //요청 보내고 응답 받기
            response = REST_TEMPLATE.postForObject(aiServerUrl, requestEntity, JsonNode.class);

            //에러 잡기
        } catch (HttpStatusCodeException e) {
            HttpStatus errorHttpStatus = HttpStatus.valueOf(e.getStatusCode().value());
            String errorResponse = e.getResponseBodyAsString();
            System.out.println("에러 1" + errorResponse);
            return null;
            //return new ResponseEntity<>(errorResponse, errorHttpStatus);
        } catch (Exception e) {
            System.out.println("에러 2" + e);
            return null;
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        System.out.println("전송 성공");
        System.out.println("entity: " + new ResponseEntity<>(response, httpStatus));

        //4,5번 : 응답받은 JSON 데이터 : item_id, item_name, item_price
        //item_id를 가져와 해당 item에 대한 응답 데이터를 보낸다.
        //응답받은 데이터
        JsonNode idValueNode = response.get("item_id");
        System.out.println(idValueNode.asLong());
        Long id = idValueNode.asLong();
        JsonNode itemNameValueNode = response.get("item_name");
        System.out.println(itemNameValueNode.asText());

        JsonNode itemPriceValueNode = response.get("item_price");
        System.out.println(itemPriceValueNode.asLong());
        //이름으로 조회해야함
        return findOneJSON(1L);

        //return new ResponseEntity<>(response, httpStatus);
    }

    //JSON형식으로 보내는 메서드
    public OneItemResult findOneJSON(Long id){
        //itemInfo 부분
        Item findItem = itemService.findOne(id);
        ItemApiController.OneItemDto oneItemDto = new ItemApiController.OneItemDto(findItem.getId(), findItem.getItemName(), findItem.getItemImg(), findItem.getDetailImg());

        //item_online_price 부분
        List<Market> findMarkets = marketService.findMarketsByItemId(id); //itemId로 관련 market 찾기
        List<ItemApiController.OneMarketDto> itemMarketDto = findMarkets.stream()
                .map(m -> new ItemApiController.OneMarketDto(m.getMarketName(), m.getMarketCode(), m.getMarketLogo(), m.getMarketPrice(), m.getMarketDeliverFee(), m.getMarketLink()))
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
        ItemApiController.ReviewItemDto reviewItemDto = new ItemApiController.ReviewItemDto(totalReviewRate, totalScent, totalClean, totalStimulation,totalSpicy, totalAmount, totalTaste, totalSugar,totalSolidity, totalAfterFeel);
        return new OneItemResult(oneItemDto, reviewItemDto, itemMarketDto);
    }



//    @RequestMapping("/test_rest_template_get")
//    @ResponseBody
//    public ResponseEntity<?> test2(List<MultipartFile> image) {
//        try {
//            image.forEach(img -> {
//                System.out.println(img);
//                System.out.println(img.getContentType());
//                System.out.println(img.getOriginalFilename());
//            });
//
//            HashMap<String, String> resultMap = new HashMap<>();
//            resultMap.put("result", "success");
//            return ResponseEntity.ok(resultMap);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

//
//    // Download image
//    @GetMapping("image/{fileName}")
//    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName) {
//
//        byte[] downloadImage = storageService.downloadImage(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.IMAGE_PNG)
//                .body(downloadImage);
//    }
}
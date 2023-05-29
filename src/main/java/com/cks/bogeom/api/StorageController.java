package com.cks.bogeom.api;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StorageController {


    final private StorageService storageService;

    // application.yml 파일에서 AI 서버 URL 읽어오기
    @Value("${ai.server.url}")
    private String aiServerUrl;


    // Upload image
    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = storageService.uploadImage(file); // Save the original file name

        String url = "http://localhost:8080/api/image/" + file.getOriginalFilename();

        // Trigger the downloadImage method
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.TEXT_PLAIN)
                .body(url);
    }
    private static final RestTemplate REST_TEMPLATE;


    static {
        // RestTemplate 기본 설정을 위한 Factory 생성
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(16000);
        factory.setReadTimeout(16000);
//        factory.setBufferRequestBody(false);
        REST_TEMPLATE = new RestTemplate(factory);
    }

    //ai 서버에 이미지 전달하는 API
    @PostMapping("/proxyUpload")
    public ResponseEntity<?> uploadImages(@RequestParam("image") MultipartFile image) throws IOException {
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
            data.add("image", resource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(data, headers);


            response = REST_TEMPLATE.postForObject(aiServerUrl, requestEntity, JsonNode.class);

        } catch (HttpStatusCodeException e) {
            HttpStatus errorHttpStatus = HttpStatus.valueOf(e.getStatusCode().value());
            String errorResponse = e.getResponseBodyAsString();
            System.out.println("에러 1" + errorResponse);
            return new ResponseEntity<>(errorResponse, errorHttpStatus);
        } catch (Exception e) {
            System.out.println("에러 2" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        System.out.println("전송 성공");
        System.out.println("entity: " + new ResponseEntity<>(response, httpStatus));

        return new ResponseEntity<>(response, httpStatus);
    }



    @RequestMapping("/test_rest_template_get")
    @ResponseBody
    public ResponseEntity<?> test2(List<MultipartFile> image) {
        try {
            image.forEach(img -> {
                System.out.println(img);
                System.out.println(img.getContentType());
                System.out.println(img.getOriginalFilename());
            });

            HashMap<String, String> resultMap = new HashMap<>();
            resultMap.put("result", "success");
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Download image
    @GetMapping("image/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName) {

        byte[] downloadImage = storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(downloadImage);
    }
}
//package com.cks.bogeom.api;
//
//import com.cks.bogeom.utils.ImageUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.*;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import org.springframework.web.client.HttpStatusCodeException;
//import com.fasterxml.jackson.databind.JsonNode;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class ImageApiController {
//
//    private static final RestTemplate REST_TEMPLATE ;
//
//    static {
//        // RestTemplate 기본 설정을 위한 Factory 생성
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(3000);
//        factory.setReadTimeout(3000);
//        factory.setBufferRequestBody(false); // 파일 전송은 이 설정을 꼭 해주자.
//        REST_TEMPLATE = new RestTemplate(factory);
//    }
//
//    @RequestMapping(value = "/proxyUpload", method = RequestMethod.POST)
//    public ResponseEntity<?> uploadImages(MultipartFile file) throws IOException {
//
//        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//        JsonNode response;
//        HttpStatus httpStatus = HttpStatus.CREATED;
//
//        try {
//            if (!file.isEmpty()) {
////                    map.add("files", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
//                map.add("file", file.getResource());
//            }
//            //map.add("stringValue", "string!"); // 문자열도 가능
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//            String url = "http://localhost:8080/test_rest_template_get";
//
//            HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity
//                    = new HttpEntity<>(map, headers);
//
//            response = REST_TEMPLATE.postForObject(url, httpEntity, JsonNode.class);
//
//        } catch (HttpStatusCodeException e) {
//            HttpStatus errorHttpStatus = HttpStatus.valueOf(e.getStatusCode().value());
//            String errorResponse = e.getResponseBodyAsString();
//            return new ResponseEntity<>(errorResponse, errorHttpStatus);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//
//        return new ResponseEntity<>(response, httpStatus);
//    }
//
//    @RequestMapping("/test_rest_template_get") //get
//    @ResponseBody
//    public ResponseEntity<?> test2(MultipartFile file) throws IOException{
//        System.out.println(file.getContentType());
//        System.out.println(file.getOriginalFilename());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", file.getContentType());
//        headers.add("Content-Length",String.valueOf(file.getBytes().length));
//
////        HashMap<String, String> resultMap = new HashMap<>();
////        resultMap.put("result", "success");
////        return ResponseEntity.ok(resultMap);
//
//        byte[] downloadImage = ImageUtils.decompressImage(file.getBytes());
//        // Trigger the downloadImage method
//        return new ResponseEntity<byte[]>(file.getBytes(), headers, HttpStatus.OK);
//    }
//
//}